package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import xyz.upperlevel.opencraft.client.OpenCraftClient;
import xyz.upperlevel.opencraft.common.network.SingleplayerClient;
import xyz.upperlevel.opencraft.common.network.packet.AskChunkAreaPacket;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.ulge.util.Color;

public class RenderArea {

    public static final int RADIUS = 2;

    public static final int SIDE = RADIUS * 2 + 1;

    @Getter
    private RenderChunk[][][] chunks = new RenderChunk[SIDE][SIDE][SIDE];

    @Getter
    private int centerX, centerY, centerZ;

    public RenderArea() {
    }

    public void build() {
        long sa = System.currentTimeMillis();
        for (int x = 0; x < SIDE; x++)
            for (int y = 0; y < SIDE; y++)
                for (int z = 0; z < SIDE; z++)
                    demandChunk(x, y, z);
        System.out.println("CLIENT> IMPORTANT!!! time took to generate chunk: " + (System.currentTimeMillis() - sa));
    }

    public RenderArea setCenterX(int absX) {
        centerX = absX;
        build();
        return this;
    }

    public RenderArea setCenterY(int absY) {
        centerY = absY;
        build();
        return this;
    }

    public RenderArea setCenterZ(int absZ) {
        centerZ = absZ;
        build();
        return this;
    }

    public RenderArea setCenter(int absX, int absY, int absZ) {
        centerX = absX;
        centerY = absY;
        centerZ = absZ;
        build();
        return this;
    }

    public RenderChunk getChunk(int x, int y, int z) {
        return chunks[x][y][z];
    }

    public int getAbsoluteX(int x) {
        return centerX - RADIUS + x;
    }

    public int getAbsoluteY(int y) {
        return centerY - RADIUS + y;
    }

    public int getAbsoluteZ(int z) {
        return centerZ - RADIUS + z;
    }

    public RenderArea demandChunk(int x, int y, int z) {
        int abs_x = getAbsoluteX(x);
        int abs_y = getAbsoluteY(y);
        int abs_z = getAbsoluteZ(z);

        System.out.println("Client> Asking chunk at x=" + abs_x + " y=" + abs_y + " z=" + abs_z);
        SingleplayerClient.connection().sendPacket(new AskChunkAreaPacket(
                abs_x,
                abs_y,
                abs_z
        ));
        return this;
    }

    public RenderArea setChunk(int x, int y, int z, RenderChunk chunk) {
        chunks[x][y][z] = chunk;
        return this;
    }

    public void destroy() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                for (int z = 0; z < SIDE; z++) {
                    RenderChunk rc = chunks[x][y][z];
                    if (rc != null) {
                        chunks[x][y][z].destroy();
                        chunks[x][y][z] = null;
                    }
                }
            }
        }
    }

    public static void main(String[] ar) {
        System.out.println("floor: " + Math.floor(-0.1));
    }

    public void draw(Uniformer uniformer) {
        Matrix4f m = new Matrix4f();
        m.translate(
                2f * 16f * centerX,
                2f * 16f * centerY,
                2f * 16f * centerZ
        );

        System.out.println("center coords: " + centerX + " " + centerY + " " + centerZ);

        WorldViewer wv = OpenCraftClient.get().getViewer();
        System.out.println("world viewer chunk coords: " + wv.getChunkX() + " " + wv.getChunkY() + " " + wv.getChunkZ());
        System.out.println("world viewer world coords: " + wv.getX() + " " + wv.getY() + " " + wv.getZ());

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
