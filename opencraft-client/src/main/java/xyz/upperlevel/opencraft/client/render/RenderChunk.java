package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.block.BlockShape;
import xyz.upperlevel.opencraft.client.block.TestBlockShape;
import xyz.upperlevel.opencraft.common.world.CommonChunk;
import xyz.upperlevel.ulge.opengl.buffer.BufferCopier;
import xyz.upperlevel.ulge.opengl.buffer.DrawMode;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;

import java.nio.ByteBuffer;

public class RenderChunk {

    @Getter
    private Vbo vbo = new Vbo();

    @Getter
    private int verticesCount = 0;

    private BlockShape blocks[] = new BlockShape[CommonChunk.SIZE];
    private int[] vboOffsets = new int[CommonChunk.SIZE];

    public RenderChunk() {
        long sa = System.currentTimeMillis();
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    setShape(x, y, z, TestBlockShape.inst);
                }
            }
        }
        recalcVerticesCount();
        System.out.println("time to generate chunk buf=" + (System.currentTimeMillis() - sa));
    }

    public static int getId(int x, int y, int z) {
        return x << 4 * 2 | y << 4 | z;
    }

    public static int getX(short id) {
        return (id & 0xf00) >> 4 * 2;
    }

    public static int getY(short id) {
        return (id & 0x0f0) >> 4;
    }

    public static int getZ(short id) {
        return id & 0x00f;
    }

    public BlockShape getShape(int id) {
        return blocks[id];
    }

    public BlockShape getShape(int x, int y, int z) {
        return blocks[getId(x, y, z)];
    }

    public void setShape(int x, int y, int z, BlockShape renderModel) {
        setShape(getId(x, y, z), renderModel);
    }

    private void recalcVerticesCount() {
        verticesCount = 0;
        for (BlockShape block : blocks) {
            if (block != null) {
                verticesCount += block.getVerticesCount();
            }

            System.out.println("DEBUG! ELIMINATE IT NOW!");
        }
    }

    public void setShape(int id, BlockShape shape) {
        blocks[id] = shape;

        long sta = System.currentTimeMillis();
        vboOffsets[id] = id > 0 ? vboOffsets[id - 1] : 0;
        for (int i = id; i < blocks.length - 1; i++) {
            int fromOffset = vboOffsets[id];
            int toOffset = fromOffset + shape.getVerticesCount();

            // moves vertices inside of the vbo
            BufferCopier.copy(vbo, vbo, fromOffset, toOffset, blocks[id].getVerticesCount());

            // pushes data inside the vbo
            ByteBuffer bfr = BufferUtils.createByteBuffer(shape.getDataCount());
            shape.compileBuffer(bfr, new Matrix4f());
            vbo.updateData(fromOffset, bfr);

            // sets up offsets
            vboOffsets[id + 1] = toOffset;
        }
    }

    public void draw() {
        vbo.bind();
        vbo.draw(DrawMode.QUADS, 0, verticesCount);
        System.out.println("verticesCount: " + verticesCount);
    }

    public void destroy() {
        vbo.destroy();
    }
}
