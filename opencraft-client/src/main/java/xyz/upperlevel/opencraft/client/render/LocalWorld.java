package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.server.network.SingleplayerClient;
import xyz.upperlevel.opencraft.server.network.packet.AskChunkAreaPacket;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.Color;

public class LocalWorld {

    public static final int MAX_RADIUS = 2;

    public static final int SIDE = MAX_RADIUS * 2 + 1;

    @Getter
    private int centerX, centerY, centerZ;

    @Getter
    private LocalChunk[][][] chunks = new LocalChunk[SIDE][SIDE][SIDE];

    public LocalWorld() {
    }

    public int getCornerX() {
        return centerX - MAX_RADIUS;
    }

    public int getCornerY() {
        return centerY - MAX_RADIUS;
    }

    public int getCornerZ() {
        return centerZ - MAX_RADIUS;
    }

    public void setCenterX(int x) {
        if (x != centerX) {
            centerX = x;
            demand();
        }
    }

    public void setCenterY(int y) {
        if (y != centerY) {
            centerY = y;
            demand();
        }
    }

    public void setCenterZ(int z) {
        if (z != centerZ) {
            centerZ = z;
            demand();
        }
    }

    public void setCenter(int x, int y, int z) {
        if (x != centerX || y != centerY || z != centerZ) {
            centerX = x;
            centerY = y;
            centerZ = z;
            demand();
        }
    }

    private int toLocWrlX(int x) {
        return x - centerX + MAX_RADIUS;
    }

    private int toLocWrlY(int y) {
        return y - centerY + MAX_RADIUS;
    }

    private int toLocWrlZ(int z) {
        return z - centerZ + MAX_RADIUS;
    }

    public LocalChunk getChunk(int x, int y, int z) {
        int lwx = toLocWrlX(x);
        int lwy = toLocWrlY(y);
        int lwz = toLocWrlZ(z);

        if (lwx < 0 || lwx >= SIDE || lwy < 0 || lwy >= SIDE || lwz < 0 || lwz >= SIDE)
            return null;

        return chunks[lwx][lwy][lwz];
    }

    public void setChunk(int x, int y, int z, LocalChunk chunk) {
        int lwx = toLocWrlX(x);
        int lwy = toLocWrlY(y);
        int lwz = toLocWrlZ(z);

        if (lwx < 0 || lwx >= SIDE || lwy < 0 || lwy >= SIDE || lwz < 0 || lwz >= SIDE)
            return;

        chunks[lwx][lwy][lwz] = chunk;
    }

    public BlockShape getShape(int x, int y, int z) {
        LocalBlock b = getBlock(x, y, z);
        return b != null ? b.getShape() : null;
    }

    public LocalBlock getBlock(int x, int y, int z) {
        int cx = (int) Math.floor(x / 16f);
        int cy = (int) Math.floor(y / 16f);
        int cz = (int) Math.ceil(z / 16f);

        int bx = Math.floorMod(x, 16);
        int by = Math.floorMod(y, 16);
        int bz = Math.floorMod(z, 16);

        LocalChunk c = getChunk(cx, cy, cz);
        return c != null ? c.getBlock(bx, by, bz) : null;
    }

    public void demand() {
        int crx = getCornerX();
        int cry = getCenterY();
        int crz = getCornerZ();
        
        for (int x = 0; x < SIDE; x++)
            for (int y = 0; y < SIDE; y++)
                for (int z = 0; z < SIDE; z++)
                    demandChunk(crx + x, cry + y, crz + z);
        
        // todo chunks will not be received all since is asynchronously (now is sync)
        build();
    }

    public void demandChunk(int x, int y, int z) {
        SingleplayerClient.connection().sendPacket(new AskChunkAreaPacket(
                x,
                y,
                z
        ));
    }

    public void build() {
        for (int x = 0; x < SIDE; x++)
            for (int y = 0; y < SIDE; y++)
                for (int z = 0; z < SIDE; z++) {
                    LocalChunk c = chunks[x][y][z];
                    if (c != null)
                        c.build();
                }
    }

    public void destroy() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                for (int z = 0; z < SIDE; z++) {
                    LocalChunk c = chunks[x][y][z];
                    if (c != null) {
                        chunks[x][y][z].destroy();
                        chunks[x][y][z] = null;
                    }
                }
            }
        }
    }

    public void draw(Uniformer uniformer) {
        Matrix4f m = new Matrix4f();
        m.translate(
                2f * 16f * centerX,
                2f * 16f * centerY,
                2f * 16f * centerZ
        );
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                for (int z = 0; z < SIDE; z++) {
                    if (chunks[x][y][z] != null) {
                        Matrix4f model = new Matrix4f(m);
                        model.translate(
                                2f * 16f * (x - MAX_RADIUS),
                                2f * 16f * (y - MAX_RADIUS),
                                2f * 16f * (z - MAX_RADIUS) * -1f
                        );
                        uniformer.setUniformMatrix4("model", model.get(BufferUtils.createFloatBuffer(16)));
                        uniformer.setUniform("uni_col", Color.rgba(((float) x) / SIDE, ((float) y) / SIDE, 1f - ((float) z) / SIDE, 1f));

                        chunks[x][y][z].draw();
                    }
                }
            }
        }
    }
}
