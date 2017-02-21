package xyz.upperlevel.opencraft.client.render;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL31;
import xyz.upperlevel.opencraft.server.world.Chunk;
import xyz.upperlevel.ulge.opengl.buffer.Vbo;

import static org.lwjgl.opengl.GL31.glCopyBufferSubData;

public class RenderChunk {

    private Vbo vbo;

    private BlockShape blocks[] = new BlockShape[Chunk.SIZE];
    private int[] vboOffsets = new int[Chunk.SIZE];

    public RenderChunk() {
    }

    public int getId(int x, int y, int z) {
        return x << 8 * 2 | y << 8 | z << Byte.BYTES;
    }

    public int getX(short id) {
        return id & (0xff0000 >> 8 + 4);
    }

    public int getY(short id) {
        return id & 0x00ff00;
    }

    public int getZ(short id) {
        return id & 0x0000ff;
    }

    public void setBlock(int id, BlockShape renderModel) {
        if (id < 0)
            throw new IndexOutOfBoundsException("id < 0");
        blocks[id] = renderModel;
        vboOffsets[id] = id > 0 ? vboOffsets[id - 1] : 0;
        for (int i = id; i < blocks.length; i++) {
            int fromOffset = vboOffsets[id];
            int toOffset = fromOffset + blocks[id].getVerticesCount();

            // moves vbo data
            glCopyBufferSubData(GL31.GL_COPY_READ_BUFFER, GL31.GL_COPY_WRITE_BUFFER, fromOffset, toOffset, );
            // moves vbo offsets
            vboOffsets[id + 1] = toOffset;
        }
    }

    public void setBlock(int x, int y, int z, BlockShape renderModel) {
        setBlock(getId(x, y, z), renderModel);
    }

    public void destroy() {
        vbo.destroy();
    }
}
