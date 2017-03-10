package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.server.network.SingleplayerClient;
import xyz.upperlevel.opencraft.server.network.packet.AskChunkAreaPacket;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.Color;

import java.util.LinkedList;
import java.util.Queue;

public class ViewRenderer {

    @Getter
    private ViewerRenderer viewer;

    public static final int RADIUS = 2;

    public static final int SIDE = RADIUS * 2 + 1;

    @Getter
    private ChunkRenderer[][][] chunks = new ChunkRenderer[SIDE][SIDE][SIDE];

    @Getter
    private int centerX, centerY, centerZ;

    public ViewRenderer(@NonNull ViewerRenderer viewer) {
        this.viewer = viewer;
    }

    public boolean isOut(int x, int y, int z) {
        return x < 0 || y < 0 || z < 0 || x >= SIDE || y >= SIDE || z >= SIDE;
    }

    public BlockShape getShape(int x, int y, int z) {
        BlockRenderer b = getBlock(x, y, z);
        return b != null ? b.getShape() : null;
    }

    public BlockRenderer getBlock(int x, int y, int z) {
        int cx = x / 16;
        int cy = y / 16;
        int cz = z / 16;

        int cbx = x % 16;
        int cby = y % 16;
        int cbz = z % 16;

        ChunkRenderer chunk = isOut(cx, cy, cz) ? null : chunks[cx][cy][cz];
        return chunk != null ? chunk.getBlock(cbx, cby, cbz) : null;
    }

    public Queue<BlockRenderer> getCollidingBlocks(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        Queue<BlockRenderer> result = new LinkedList<>();

        if (fromX == toX && fromY == toY && fromZ == toZ) {
            result.add(getBlock(fromX, fromY, fromZ));
            return result;
        }

        int dx = toX - fromX;
        int dy = toY - fromY;
        int dz = toZ - fromZ;

        int ax = Math.abs(dx) << 1;
        int ay = Math.abs(dy) << 1;
        int az = Math.abs(dz) << 1;

        int signX = (int) Math.signum(dx);
        int signY = (int) Math.signum(dy);
        int signZ = (int) Math.signum(dz);

        int x = fromX;
        int y = fromY;
        int z = fromZ;

        int deltaX, deltaY, deltaZ;
        if (ax >= Math.max(ay, az)) /* x dominant */ {
            deltaY = ay - (ax >> 1);
            deltaZ = az - (ax >> 1);
            while (true) {
                result.offer(getBlock(x, y, z));
                if (x == toX) {
                    return result;
                }

                if (deltaY >= 0) {
                    y += signY;
                    deltaY -= ax;
                }

                if (deltaZ >= 0) {
                    z += signZ;
                    deltaZ -= ax;
                }

                x += signX;
                deltaY += ay;
                deltaZ += az;
            }
        } else if (ay >= Math.max(ax, az)) /* y dominant */ {
            deltaX = ax - (ay >> 1);
            deltaZ = az - (ay >> 1);
            while (true) {
                result.offer(getBlock(x, y, z));
                if (y == toY) {
                    return result;
                }

                if (deltaX >= 0) {
                    x += signX;
                    deltaX -= ay;
                }

                if (deltaZ >= 0) {
                    z += signZ;
                    deltaZ -= ay;
                }

                y += signY;
                deltaX += ax;
                deltaZ += az;
            }
        } else if (az >= Math.max(ax, ay)) /* z dominant */ {
            deltaX = ax - (az >> 1);
            deltaY = ay - (az >> 1);
            while (true) {
                result.offer(getBlock(x, y, z));
                if (z == toZ) {
                    return result;
                }

                if (deltaX >= 0) {
                    x += signX;
                    deltaX -= az;
                }

                if (deltaY >= 0) {
                    y += signY;
                    deltaY -= az;
                }

                z += signZ;
                deltaX += ax;
                deltaY += ay;
            }
        }
        return result;
    }

    public void demand() {
        for (int x = 0; x < SIDE; x++)
            for (int y = 0; y < SIDE; y++)
                for (int z = 0; z < SIDE; z++)
                    demandChunk(x, y, z);
        // todo chunks will not be received all since is asynchronously (now is sync)
        build();
    }

    public void demandChunk(int x, int y, int z) {
        int absX = (int) getAbsX(x);
        int absY = (int) getAbsY(y);
        int absZ = (int) getAbsZ(z);

        SingleplayerClient.connection().sendPacket(new AskChunkAreaPacket(
                absX,
                absY,
                absZ
        ));
    }

    public void build() {
        for (int x = 0; x < SIDE; x++)
            for (int y = 0; y < SIDE; y++)
                for (int z = 0; z < SIDE; z++) {
                    ChunkRenderer chunk = chunks[x][y][z];
                    if (chunk != null)
                        chunk.build();
                }
    }

    public ViewRenderer setCenterX(int absX) {
        centerX = absX;
        demand();
        return this;
    }

    public ViewRenderer setCenterY(int absY) {
        centerY = absY;
        demand();
        return this;
    }

    public ViewRenderer setCenterZ(int absZ) {
        centerZ = absZ;
        demand();
        return this;
    }

    public ViewRenderer setCenter(int absX, int absY, int absZ) {
        centerX = absX;
        centerY = absY;
        centerZ = absZ;
        demand();
        return this;
    }

    public ChunkRenderer getChunk(int x, int y, int z) {
        return chunks[x][y][z];
    }

    public float getViewX(float worldX) {
        return worldX - (centerX - RADIUS) * 16;
    }

    public float getViewY(float worldY) {
        return worldY - (centerY - RADIUS) * 16;
    }

    public float getViewZ(float worldZ) {
        return worldZ - (centerZ - RADIUS) * 16;
    }

    public float getAbsX(float x) {
        return centerX - RADIUS + x;
    }

    public float getAbsY(float y) {
        return centerY - RADIUS + y;
    }

    public float getAbsZ(float z) {
        return centerZ - RADIUS + z;
    }

    public ViewRenderer setChunk(int x, int y, int z, ChunkRenderer chunk) {
        chunks[x][y][z] = chunk;
        return this;
    }

    public void destroy() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                for (int z = 0; z < SIDE; z++) {
                    ChunkRenderer rc = chunks[x][y][z];
                    if (rc != null) {
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
                                2f * 16f * (x - RADIUS),
                                2f * 16f * (y - RADIUS),
                                2f * 16f * (z - RADIUS) * -1f
                        );
                        uniformer.setUniformMatrix4("model", model.get(BufferUtils.createFloatBuffer(16)));
                        uniformer.setUniform("uni_col", Color.rgba(((float) x) / SIDE, ((float) y) / SIDE, 1f - ((float) z) / SIDE, 1f));

                        getChunk(x, y, z).draw();
                    }
                }
            }
        }
    }
}
