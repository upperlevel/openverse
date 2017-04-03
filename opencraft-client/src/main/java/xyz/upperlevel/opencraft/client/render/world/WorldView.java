package xyz.upperlevel.opencraft.client.render.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.client.render.Rendering;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.World;
import xyz.upperlevel.ulge.opengl.shader.Program;

import static java.lang.Math.floor;
import static java.lang.Math.floorMod;
import static org.lwjgl.BufferUtils.createFloatBuffer;
import static xyz.upperlevel.opencraft.world.Chunk.*;

public class WorldView implements World {

    // world

    @Getter
    private int radius, side;

    @Getter
    @Setter
    private int x, y, z;

    @Getter
    private BufferedChunk[][][] chunks;

    public WorldView(int radius) {
        this.radius = radius;
        side = radius * 2 + 1;
        chunks = new BufferedChunk[side][side][side];
    }

    public void setCenter(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        // todo load new chunks
    }

    private int getIndexX(int x) {
        return x - this.x + radius;
    }

    private int getIndexY(int y) {
        return y - this.y + radius;
    }

    private int getIndexZ(int z) {
        return z - this.z + radius;
    }

    @Override
    public Chunk getChunk(int x, int y, int z) {
        int lx = getIndexX(x);
        int ly = getIndexY(y);
        int lz = getIndexZ(z);

        if (lx < 0 || lx >= side || ly < 0 || ly >= side || lz < 0 || lz >= side)
            return null;
        return chunks[lx][ly][lz];
    }

    public void setChunk(int x, int y, int z, BufferedChunk chunk) {
        chunks[x][y][z] = chunk;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        int lx = (int) floor(x / (float) WIDTH);
        int ly = (int) floor(y / (float) HEIGHT);
        int lz = (int) floor(z / (float) LENGTH);

        int bx = floorMod(x, WIDTH);
        int by = floorMod(y, HEIGHT);
        int bz = floorMod(z, LENGTH);

        Chunk c = getChunk(lx, ly, lz);
        return c != null ? c.getBlock(bx, by, bz) : null;
    }

    public void render() {
        Program program = Rendering.get()
                .programs()
                .getEnabled();

        if (program == null)
            return;

        for (int x = 0; x < side; x++) {
            for (int y = 0; y < side; y++) {
                for (int z = 0; z < side; z++) {
                    BufferedChunk chunk = chunks[x][y][z];
                    if (chunk != null) {
                        Matrix4f in = new Matrix4f()
                                .translate(16 * x, 16 * y, 16 * z);
                        program.uniformer.setUniformMatrix4(
                                "model",
                                in.get(createFloatBuffer(16))
                        );

                        chunk.render();
                    }
                }
            }
        }
    }
}
