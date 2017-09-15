package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.client.resource.model.ClientModel;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.BlockSystem;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.ulge.opengl.buffer.*;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Uniform;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static xyz.upperlevel.openverse.world.chunk.Chunk.*;

/**
 * This class renders a chunk in a specified location.
 */
@Getter
public class ChunkRenderer {
    private final Program program;
    private final ChunkLocation location;
    private Vao vao;
    private Vbo vbo;
    private Uniform modelLoc;
    private final BlockType[][][] blockTypes = new BlockType[WIDTH][HEIGHT][LENGTH];
    private AtomicBoolean alive = new AtomicBoolean(true);
    @Setter
    private Consumer<ChunkRenderer> updater;

    //Cache
    private FloatBuffer model;

    private int
            allocateVerticesCount = 0, // vertices to allocate on vbo init
            allocateDataCount = 0; // data to allocate on vbo init

    private int
            drawVerticesCount = 0; // draw vertices count on drawing

    public ChunkRenderer(Chunk chunk, Program program) {
        this.program = program;
        this.location = chunk.getLocation();
        modelLoc = program.uniformer.get("model");
        setup();
        load(chunk);
    }

    public void load(Chunk chunk) {
        load(chunk.getBlockSystem());
    }

    public void load(BlockSystem blockSystem) {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                for (int z = 0; z < LENGTH; z++)
                    setBlockType(blockSystem.getBlock(x, y, z), false);
        if(updater != null)
            updater.accept(this);
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blockTypes[x][y][z];
    }

    public void setBlockType(int x, int y, int z, BlockType type, boolean update) {
        ClientModel oldModel = blockTypes[x][y][z] != null ? (ClientModel) blockTypes[x][y][z].getModel() : null;
        ClientModel newModel = type != null ? (ClientModel) type.getModel() : null;

        blockTypes[x][y][z] = type;

        // gets vertices/data count for old and new model
        int oldVrt = oldModel == null ? 0 : oldModel.getVerticesCount();
        int newVrt = newModel == null ? 0 : newModel.getVerticesCount();

        int oldData = oldModel == null ? 0 : oldModel.getDataCount();
        int newData = newModel == null ? 0 : newModel.getDataCount();

        // updates vertices/data count
        allocateVerticesCount += newVrt - oldVrt;
        allocateDataCount += newData - oldData;

        // rebuilds chunk if requested
        if (update && updater != null)
            updater.accept(this);
    }

    public void setBlockType(int x, int y, int z, BlockType type) {
        setBlockType(x, y, z, type, true);
    }

    public void setBlockType(@NonNull Block block, boolean update) {
        setBlockType(
                block.getX() & 15,
                block.getY() & 15,
                block.getZ() & 15,
                block.getType(),
                update
        );
    }

    public void setBlockType(@NonNull Block block) {
        setBlockType(block, true);
    }

    public void setup() {
        vao = new Vao();
        vao.bind();

        vbo = new Vbo();
        vbo.bind();
        new VertexLinker()
                .attrib(program.uniformer.getAttribLocation("position"), 3)
                .attrib(program.uniformer.getAttribLocation("color"), 4)
                .attrib(program.uniformer.getAttribLocation("texCoords"), 3)
                .setup();

        vbo.unbind();
        vao.unbind();

        model = new Matrix4f().translation(location.x << 4, location.y << 4, location.z << 4).get(BufferUtils.createFloatBuffer(16));
    }

    public int compile(ByteBuffer buffer) {
        int vertexCount = 0;
        Matrix4f in = new Matrix4f();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < LENGTH; z++) {
                    BlockType ty = blockTypes[x][y][z];
                    if (ty != null) {
                        ClientModel model = (ClientModel) ty.getModel();
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
        if(drawVerticesCount != 0) {
            //System.out.println("Rendering: " + location + " -> " + drawVerticesCount);
            modelLoc.matrix4(model);
            vao.bind();
            vao.draw(DrawMode.QUADS, 0, drawVerticesCount);
        }
    }

    public boolean isAlive() {
        return alive.get();
    }

    public void destroy() {
        //Openverse.logger().warning("Destroying VBO for chunk: " + location);
        alive.set(false);
        vbo.destroy();
        vao.destroy();
        vao = null;
    }
}
