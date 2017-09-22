package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.client.render.block.BlockModel;
import xyz.upperlevel.openverse.client.render.block.BlockTypeModelMapper;
import xyz.upperlevel.openverse.client.render.world.util.VertexBufferPool;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static xyz.upperlevel.openverse.world.chunk.Chunk.*;

/**
 * This class renders a chunk in a specified location.
 */
@Getter
public class ChunkRenderer {
    private final Program program;
    private final ChunkLocation location;
    private final ChunkViewRenderer parent;
    private Chunk handle;
    private Vao vao;
    private Vbo vbo;
    private Uniform modelLoc;
    private ChunkCompileTask compileTask;

    //Cache
    private FloatBuffer model;

    private int
            allocateVerticesCount = 0, // vertices to allocate on vbo init
            allocateDataCount = 0; // data to allocate on vbo init

    private int
            drawVerticesCount = 0; // draw vertices count on drawing

    public ChunkRenderer(ChunkViewRenderer parent, Chunk chunk, Program program) {
        this.parent = parent;
        this.program = program;
        this.location = chunk.getLocation();
        this.handle = chunk;
        modelLoc = program.uniformer.get("model");
        setup();
        reloadVertexSize();
        parent.recompileChunk(this, ChunkCompileMode.ASYNC);
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
        BlockStorage storage = handle.getBlockStorage();
        int vertexCount = 0;
        int dataCount = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < LENGTH; z++) {
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
        vao = new Vao();
        vao.bind();

        vbo = new Vbo();
        vbo.bind();
        new VertexLinker()
                .attrib(program.uniformer.getAttribLocation("position"), 3)
                .attrib(program.uniformer.getAttribLocation("texCoords"), 3)
                .setup();

        vbo.unbind();
        vao.unbind();

        model = new Matrix4f().translation(location.x << 4, location.y << 4, location.z << 4).get(BufferUtils.createFloatBuffer(16));
    }

    public ChunkCompileTask createCompileTask(VertexBufferPool pool) {
        if (compileTask != null) {
            compileTask.abort();
        }
        compileTask = new ChunkCompileTask(pool, this);
        return compileTask;
    }

    public int compile(ByteBuffer buffer) {
        BlockStorage storage = handle.getBlockStorage();
        int vertexCount = 0;
        Matrix4f in = new Matrix4f();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < LENGTH; z++) {
                    BlockState state = storage.getBlockState(x, y, z);
                    if (state != null) {
                        BlockModel model = BlockTypeModelMapper.model(state);
                        if (model != null)
                            vertexCount += model.store(in.translation(x, y, z), buffer);
                    }
                }
            }
        }
        buffer.flip();
        //Openverse.logger().info("Vertices computed for chunk at: " + location + " -> " + vertexCount);
        return vertexCount;
    }

    public void setVertices(ByteBuffer vertices, int vertexCount) {
        vbo.loadData(vertices, VboDataUsage.STATIC_DRAW);
        this.drawVerticesCount = vertexCount;
    }

    @SuppressWarnings("deprecation")
    public void render(Program program) {
        //TODO: draw TileEntities
        if (drawVerticesCount != 0) {
            //System.out.println("Rendering: " + location + " -> " + drawVerticesCount);
            modelLoc.matrix4(model);
            vao.bind();
            vao.draw(DrawMode.QUADS, 0, drawVerticesCount);
        }
    }

    public void destroy() {
        //Openverse.logger().warning("Destroying VBO for chunk: " + location);
        if (compileTask != null) {
            compileTask.abort();
        }
        vbo.destroy();
        vao.destroy();
    }
}
