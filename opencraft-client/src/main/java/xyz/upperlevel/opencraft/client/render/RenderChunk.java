package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.block.BlockShape;
import xyz.upperlevel.opencraft.client.block.registry.BlockRegistry;
import xyz.upperlevel.opencraft.client.block.registry.BlockType;
import xyz.upperlevel.opencraft.common.world.BridgeBlockType;
import xyz.upperlevel.opencraft.common.world.ChunkArea;
import xyz.upperlevel.ulge.opengl.DataType;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;
import xyz.upperlevel.ulge.opengl.buffer.VboDataUsage;
import xyz.upperlevel.ulge.opengl.buffer.VertexLinker;

import java.nio.ByteBuffer;

public class RenderChunk {

    @Getter
    private Vbo vbo = new Vbo();

    {
        vbo.bind();
        new VertexLinker(DataType.FLOAT)
                .attrib(0, 3)
                .attrib(1, 4)
                .attrib(2, 2)
                .setup();
        vbo.unbind();
    }

    @Getter
    private int verticesCount = 0, dataCount = 0;

    private RenderBlockShape[][][] shapes = new RenderBlockShape[16][16][16];

    {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    shapes[x][y][z] = new RenderBlockShape(this, x, y, z);
                }
            }
        }
    }

    public RenderChunk() {
    }

    public RenderChunk load(ChunkArea chunk) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    BridgeBlockType bType = chunk.getBlock(x, y, z);
                    BlockType type = BlockRegistry.def().getBlock(bType);
                    if (type == null) {
                        System.err.println("bridge block type '" + bType.getId() + "' has not been found");
                        continue;
                    }
                    setShape(x, y, z, type.getShape(), false);
                }
            }
        }
        buildVbo();
        return this;
    }

    public RenderBlockShape getShape(int x, int y, int z) {
        return shapes[x][y][z];
    }

    public void setShape(int x, int y, int z, BlockShape shape) {
        setShape(x, y, z, shape, true);
    }

    public void setShape(int x, int y, int z, BlockShape shape, boolean buildVbo) {
        BlockShape oldShape = shapes[x][y][z].getShape();

        shapes[x][y][z].setShape(shape);

        int ovc = oldShape != null ? oldShape.getVerticesCount() : 0;
        int nvc = shape != null ? shape.getVerticesCount() : 0;

        int odc = oldShape != null ? oldShape.getDataCount() : 0;
        int ndc = shape != null ? shape.getDataCount() : 0;

        verticesCount += nvc - ovc;
        dataCount += ndc - odc;

        if (buildVbo)
            buildVbo();
    }

    public void buildVbo() {
        ByteBuffer data = BufferUtils.createByteBuffer(dataCount * Float.BYTES);
        for (int x = 0; x < 16; x++)
            for (int y = 0; y < 16; y++)
                for (int z = 0; z < 16; z++) {
                    RenderBlockShape render = shapes[x][y][z];
                    BlockShape shape = render.getShape();
                    if (shape != null) {
                        Matrix4f model = new Matrix4f()
                                .translate(
                                        -1f + x * 2f,
                                        -1f + y * 2f,
                                        -1f + z * -2f
                                );
                        shape.compileBuffer(data, model);
                    }
                }
        data.flip();
        vbo.loadData(data, VboDataUsage.DYNAMIC_DRAW);
    }

    public void draw() {
        vbo.draw(DrawMode.QUADS, 0, verticesCount);
    }

    public void destroy() {
        vbo.destroy();
    }
}
