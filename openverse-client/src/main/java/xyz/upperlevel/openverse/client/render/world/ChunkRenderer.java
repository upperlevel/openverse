package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.openverse.client.render.block.BlockModel;
import xyz.upperlevel.openverse.client.render.block.BlockTypeModelMapper;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;

import java.nio.ByteBuffer;

/**
 * This class renders a chunk in a specified location.
 */
@Getter
public class ChunkRenderer {
    private static Uniform worldSkyLightLoc;

    private final Program program;
    private final ChunkViewRenderer view;
    private Chunk chunk;
    private Vao vao;
    private Vbo vbo;
    private ChunkCompileTask compileTask;

    private int
            allocateVerticesCount = 0, // vertices to allocate on vbo init
            allocateDataCount = 0; // data to allocate on vbo init

    private int
            drawVerticesCount = 0; // draw vertices count on drawing

    public ChunkRenderer(ChunkViewRenderer view, Chunk chunk, Program program) {
        this.view = view;
        this.program = program;
        this.chunk = chunk;
        if (worldSkyLightLoc == null) {
            worldSkyLightLoc = program.getUniform("worldSkylight");
        }
        setup();
        reloadVertexSize();
    }

    public void onBlockChange(BlockState oldState, BlockState newState) {//TODO: call whenever a block changes
        BlockModel om = BlockTypeModelMapper.model(oldState);
        BlockModel nm = BlockTypeModelMapper.model(newState);

        // gets vertices/data count for old and new model
        int oldVrt = om == null ? 0 : om.getVerticesCount();
        int newVrt = nm == null ? 0 : nm.getVerticesCount();

        int oldData = om == null ? 0 : om.getDataCount();
        int newData = nm == null ? 0 : nm.getDataCount();

        // updates vertices/data count
        allocateVerticesCount += newVrt - oldVrt;
        allocateDataCount += newData - oldData;

        // rebuilds chunk if requested
    }

    public void reloadVertexSize() {
        BlockStorage storage = chunk.getBlockStorage();
        int vertexCount = 0;
        int dataCount = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockModel model = BlockTypeModelMapper.model(storage.getBlockState(x, y, z));
                    if (model != null) {
                        vertexCount += model.getVerticesCount();
                        dataCount += model.getDataCount();
                    }
                }
            }
        }
        allocateVerticesCount = vertexCount;
        allocateDataCount = dataCount;
    }

    public void setup() {
        if (vao != null) {
            throw new IllegalStateException("Chunk already initialized!");
        }
        vao = new Vao();
        vao.bind();

        vbo = new Vbo();
        vbo.bind();

        program.use();

        VertexLinker linker = new VertexLinker();
        linker.attrib(program.getAttribLocation("position"), 3, DataType.FLOAT, false, 0);
        linker.attrib(program.getAttribLocation("texCoords"), 3, DataType.FLOAT, false, 3 * DataType.FLOAT.getByteCount());
        linker.attrib(program.getAttribLocation("blockLight"), 1, DataType.FLOAT, false, 6 * DataType.FLOAT.getByteCount());
        linker.attrib(program.getAttribLocation("blockSkylight"), 1, DataType.FLOAT, false, 7 * DataType.FLOAT.getByteCount());
        linker.setup();

        vbo.unbind();
        vao.unbind();
    }

    public ChunkCompileTask createCompileTask(VertexBufferPool pool) {
        if (compileTask != null) {
            compileTask.abort();
            compileTask = null;
        }
        compileTask = new ChunkCompileTask(pool, this);
        return compileTask;
    }

    public int compile(ByteBuffer buffer) {
        BlockStorage storage = chunk.getBlockStorage();
        int vertexCount = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockState state = storage.getBlockState(x, y, z);
                    if (state != null) {
                        BlockModel model = BlockTypeModelMapper.model(state);
                        if (model != null) {
                            vertexCount += model.renderOnBuffer(
                                    chunk.getWorld(),
                                    chunk.getX() * 16 + x,
                                    chunk.getY() * 16 + y,
                                    chunk.getZ() * 16 + z, buffer);
                        }
                    }
                }
            }
        }
        buffer.flip();
        //Openverse.getLogger().info("Vertices computed for chunk at: " + vertexCount + "(" + buffer.remaining() + ")");
        return vertexCount;
    }

    public void setVertices(ByteBuffer vertices, int vertexCount) {
        if (vertexCount > 0) {
            if (vertices.remaining() == 0) {
                throw new IllegalStateException("No vertex in buffer but " + vertexCount + " to draw");
            }
            vbo.loadData(vertices, VboDataUsage.STATIC_DRAW);
        }
        this.drawVerticesCount = vertexCount;
    }

    @SuppressWarnings("deprecation")
    public void render(Program program) {
        worldSkyLightLoc.set(chunk.getWorld().getSkylight());
        //TODO: draw TileEntities
        if (drawVerticesCount != 0) {
            vao.bind();
            vao.draw(DrawMode.QUADS, 0, drawVerticesCount);
        }
    }

    public void destroy() {
        //Openverse.getLogger().warning("Destroying VBO for chunk: " + location);
        if (compileTask != null) {
            compileTask.abort();
        }
        vbo.destroy();
        vao.destroy();
    }
}
