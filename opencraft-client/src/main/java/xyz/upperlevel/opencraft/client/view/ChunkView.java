package xyz.upperlevel.opencraft.client.view;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.common.world.Chunk;
import xyz.upperlevel.opencraft.common.world.ChunkStorer;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;
import xyz.upperlevel.ulge.opengl.buffer.VboDataUsage;
import xyz.upperlevel.ulge.opengl.buffer.VertexLinker;

import java.nio.ByteBuffer;

public class ChunkView implements Chunk {

    @Getter
    private WorldView world;

    @Getter
    private int x, y, z;

    @Getter
    private ChunkModeler shaper = new ChunkModeler();

    @Getter
    private Vbo vbo = new Vbo();

    @Getter
    private int allocVertCount = 0, allocDataCount = 0;

    public ChunkView(@NonNull WorldView view, int x, int y, int z) {
        this.world = view;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void load(ChunkStorer storer) {
        load(storer, true);
    }

    public void load(ChunkStorer storer, boolean update) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    setVoxel(x, y, z, storer.getVoxel(x, y, z), false);
                }
            }
        }
        if (update)
            build();
    }

    public BlockView getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x >= 16 || y >= 16 || z >= 16)
            return null;
        return blocks[x][y][z];
    }

    public BlockShape getShape(int x, int y, int z) {
        BlockView b = getBlock(x, y, z);
        return b != null ? b.getShape() : null;
    }

    public void setVoxel(int x, int y, int z, BlockShape shape) {
        setVoxel(x, y, z, shape, true);
    }

    public void setVoxel(int x, int y, int z, BlockShape shape, boolean rebuild) {
        BlockShape oldShape = blocks[x][y][z].getShape();

        blocks[x][y][z].setShape(shape);

        int ovc = oldShape != null ? oldShape.getVerticesCount() : 0;
        int nvc = shape != null ? shape.getVerticesCount() : 0;

        int odc = oldShape != null ? oldShape.getDataCount() : 0;
        int ndc = shape != null ? shape.getDataCount() : 0;

        allocVertCount += nvc - ovc;
        allocDataCount += ndc - odc;

        if (rebuild)
            build();
    }

    private int drawVertCount = 0;

    public void build() {
        ByteBuffer data = BufferUtils.createByteBuffer(allocDataCount * Float.BYTES);
        for (int x = 0; x < 16; x++)
            for (int y = 0; y < 16; y++)
                for (int z = 0; z < 16; z++) {
                    BlockShape shape = blocks[x][y][z].getShape();
                    if (shape != null && !shape.isEmpty()) {
                        Matrix4f model = new Matrix4f()
                                .translate(
                                        x,
                                        y,
                                        z
                                );
                        drawVertCount += shape.cleanCompile(this.x * 16 + x, this.y * 16 + y, this.z * 16 + z, world, model, data);
                    }
                }
        data.flip();
        vbo.loadData(data, VboDataUsage.DYNAMIC_DRAW);
    }

    public void draw() {
        vbo.bind();
        new VertexLinker(DataType.FLOAT)
                .attrib(0, 3)
                .attrib(1, 4)
                .attrib(2, 3)
                .setup();
        vbo.draw(DrawMode.QUADS, 0, drawVertCount);
    }

    public void destroy() {
        vbo.destroy();
    }
}
