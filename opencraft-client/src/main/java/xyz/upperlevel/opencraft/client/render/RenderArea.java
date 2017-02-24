package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import org.joml.Matrix4f;

public class RenderArea {

    public static final int DISTANCE = 1;

    public static final int CENTER_COORD = getCenterCoordinate(DISTANCE);

    public static final int SIDE = getSide(DISTANCE);

    @Getter
    private RenderChunk[][][] chunks = new RenderChunk[SIDE][SIDE][SIDE];

    public RenderArea() {
        chunks[0][0][0] = new RenderChunk();
    }

    public RenderChunk getRenderChunk(int x, int y, int z) {
        return chunks[x][y][z];
    }

    public RenderArea setRenderChunk(int x, int y, int z, RenderChunk chunk) {
        chunks[x][y][z] = chunk;
        return this;
    }

    public RenderChunk getCenterChunk() {
        return chunks[CENTER_COORD][CENTER_COORD][CENTER_COORD];
    }

    public void destroy() {
        for (int x = 0; x < SIDE; x++)
            for (int y = 0; y < SIDE; y++)
                for (int z = 0; z < SIDE; z++) {
                    chunks[x][y][z].destroy();
                    chunks[x][y][z] = null;
                }
    }

    public static int getCenterCoordinate(int distance) {
        return distance;
    }

    public static int getSide(int distance) {
        return distance * 2 + 1;
    }

    public void draw() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                for (int z = 0; z < SIDE; z++) {
                    if (chunks[x][y][z] != null) {
                        System.out.println("Drawing chunk at x=" + x  + " y=" + y + " z=" + z);
                        getRenderChunk(x, y, z).draw();
                    }
                }
            }
        }
    }
}
