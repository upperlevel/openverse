package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.OpenCraft;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.server.world.ChunkArea;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;
import xyz.upperlevel.ulge.opengl.buffer.VboDataUsage;
import xyz.upperlevel.ulge.opengl.buffer.VertexLinker;

import java.nio.ByteBuffer;

public class ChunkRenderer {

    @Getter
    private Vbo vbo;

    @Getter
    private ViewRenderer view;

    @Getter
    private int x, y, z;

    @Getter
    private int allocVertCount = 0, allocDataCount = 0;

    private BlockRenderer[][][] blocks = new BlockRenderer[16][16][16];

    {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    blocks[x][y][z] = new BlockRenderer(this, x, y, z);
                }
            }
        }
    }

    public ChunkRenderer(@NonNull ViewRenderer view, int x, int y, int z) {
        this.view = view;
        this.x = x;
        this.y = y;
        this.z = z;

        vbo = new Vbo();
    }

    public void load(ChunkArea area) {
        load(area, true);
    }

    public void load(ChunkArea area, boolean update) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    String id = area.getBlock(x, y, z).getId();
                    BlockShape shape = OpenCraft.get()
                            .getAssetManager()
                            .getShapeRegistry()
                            .getShape(id);
                    setShape(x, y, z, shape, false);
                }
            }
        }
        if (update)
            build();
    }

    public boolean isOut(int x, int y, int z) {
        return x < 0 || y < 0 || z < 0 || x >= 16 || y >= 16 || z >= 16;
    }

    public BlockRenderer getBlock(int x, int y, int z) {
        return isOut(x, y, z) ? new BlockRenderer(this, x, y, z) : blocks[x][y][z];
    }

    public BlockShape getShape(int x, int y, int z) {
        return isOut(x, y, z) ? null : blocks[x][y][z].getShape();
    }

    public void setShape(int x, int y, int z, BlockShape shape) {
        setShape(x, y, z, shape, true);
    }

    public void setShape(int x, int y, int z, BlockShape shape, boolean rebuild) {
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
                                        -1f + x * 2f,
                                        -1f + y * 2f,
                                        -1f + z * -2f
                                );
                        drawVertCount += shape.cleanCompile(this.x * 16 + x, this.y * 16 + y, this.z * 16 + z, view, model, data);
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
