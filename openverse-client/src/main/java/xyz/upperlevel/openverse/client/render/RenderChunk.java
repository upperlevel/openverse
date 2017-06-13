package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.openverse.client.resource.model.ClientModel;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.BlockSystem;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;
import xyz.upperlevel.ulge.opengl.buffer.VertexLinker;

import java.nio.ByteBuffer;

import static xyz.upperlevel.openverse.world.chunk.Chunk.*;
import static xyz.upperlevel.ulge.opengl.buffer.VboDataUsage.STATIC_DRAW;

@Getter
public class RenderChunk {

    private final ChunkLocation location;
    private final BlockType[][][] blockTypes = new BlockType[WIDTH][HEIGHT][LENGTH];
    private Vbo vbo;

    private int
            allocateVerticesCount = 0, // vertices to allocate on vbo init
            allocateDataCount = 0; // data to allocate on vbo init

    private int
            drawVerticesCount = 0; // draw vertices count on drawing

    public RenderChunk(@NonNull ChunkLocation location) {
        this.location = location;

        // just to ensure that it'll be init on constructor call
        vbo = new Vbo();
    }

    /**
     * Initializes the chunk with start blocks.
     */
    public RenderChunk(@NonNull Chunk chunk) {
        this(chunk.getLocation());
        load(chunk);
    }

    public void load(@NonNull Chunk chunk) {
        load(chunk.getBlockSystem());
    }

    public void load(@NonNull BlockSystem blockSystem) {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                for (int z = 0; z < LENGTH; z++)
                    setBlockType(blockSystem.getBlock(x, y, z), false);
        build();
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blockTypes[x][y][z];
    }

    public void setBlockType(int x, int y, int z, BlockType type, boolean update) {
        ClientModel oldModel = blockTypes[x][y][z] != null ? (ClientModel) blockTypes[x][y][z].getModel() : null;
        ClientModel newModel = type != null ? (ClientModel) type.getModel() : null;

        blockTypes[x][y][z] = type;

        // gets vertices/data count for old and new model
        int oldVrt = oldModel == null ? 0 : Graphics.getShapeCompilerManager().getVerticesCount(oldModel);
        int newVrt = newModel == null ? 0 : Graphics.getShapeCompilerManager().getVerticesCount(newModel);

        int oldData = oldModel == null ? 0 : Graphics.getShapeCompilerManager().getDataCount(oldModel);
        int newData = newModel == null ? 0 : Graphics.getShapeCompilerManager().getDataCount(newModel);

        // updates vertices/data count
        allocateVerticesCount += newVrt - oldVrt;
        allocateDataCount += newData - oldData;

        // rebuilds chunk if requested
        if (update)
            build();

        blockTypes[x][y][z] = type;
    }

    public void setBlockType(int x, int y, int z, BlockType type) {
        setBlockType(x, y, z, type, true);
    }

    public void setBlockType(@NonNull Block block, boolean update) {
        setBlockType(
                block.getX(),
                block.getY(),
                block.getZ(),
                block.getType(),
                update
        );
    }

    public void setBlockType(@NonNull Block block) {
        setBlockType(block, true);
    }

    public void build() {
        TextureBakery bakery = Graphics.getTextureBakery();

        // initializes a byte-buffer with max dimensions it can assume
        ByteBuffer buffer = BufferUtils.createByteBuffer(allocateDataCount * Float.BYTES);
        drawVerticesCount = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < LENGTH; z++) {
                    Matrix4f in = new Matrix4f()
                            .translate(x, y, z);

                    BlockType block = getBlockType(x, y, z);
                    if (block != null) {
                        ClientModel model = (ClientModel) block.getModel();
                        if (model != null)
                            drawVerticesCount += Graphics.getShapeCompilerManager().compile(model, in, buffer);
                    }
                }
            }
        }
        vbo.loadData(buffer, STATIC_DRAW);
    }

    private static final VertexLinker vertexLinker = new VertexLinker()
            .attrib(0, 3)
            .attrib(1, 4)
            .attrib(2, 3);

    @SuppressWarnings("deprecation")
    public void render() {
        vertexLinker.setup();
        // todo remove quads drawing
        vbo.draw(DrawMode.QUADS, 0, drawVerticesCount);
    }

    public void destroy() {
        vbo.destroy();
    }
}
