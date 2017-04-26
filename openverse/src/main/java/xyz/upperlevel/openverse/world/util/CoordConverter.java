package xyz.upperlevel.openverse.world.util;

import xyz.upperlevel.openverse.world.chunk.Chunk;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public final class CoordConverter {

    private CoordConverter() {
    }

    /**
     * Gets world X from chunk relative X.
     */
    public static double worldX(Chunk chunk, double cpx) {
        return (chunk.getX() << 4) + cpx;
    }

    /**
     * Gets world Y from chunk relative Y.
     */
    public static double worldY(Chunk chunk, double cpy) {
        return (chunk.getY() << 4) + cpy;
    }

    /**
     * Gets world Z from chunk relative Z.
     */
    public static double worldZ(Chunk chunk, double cpz) {
        return (chunk.getZ() << 4) + cpz;
    }

    // ---

    private static double euclideanMod(double x, int y) {
        double r = abs(x) % y;
        r *= Math.signum(x);
        r = (r + y) % y;
        return r;
    }

    /**
     * Gets chunk relative X from world X.
     */
    public static double chunkPosX(double wx) {
        return euclideanMod(wx, 16);
    }

    /**
     * Gets chunk relative X from world X.
     */
    public static int chunkPosX(int wx) {
        return wx % 0xF;
    }

    /**
     * Gets chunk relative Y from world Y.
     */
    public static double chunkPosY(double wy) {
        return euclideanMod(wy, 16);
    }

    /**
     * Gets chunk relative Y from world Y.
     */
    public static int chunkPosY(int wy) {
        return wy & 0xF;
    }

    /**
     * Gets chunk relative Z from world Z.
     */
    public static double chunkPosZ(double wz) {
        return euclideanMod(wz, 16);
    }

    /**
     * Gets chunk relative Z from world Z.
     */
    public static int chunkPosZ(int wz) {
        return wz & 0xF;
    }

    // ---

    /**
     * Gets chunk X from world X.
     */
    public static double chunkX(double wx) {
        return floor(wx / 16.);
    }

    /**
     * Gets chunk Y from world Y.
     */
    public static double chunkY(double wy) {
        return floor(wy / 16.);
    }

    /**
     * Gets chunk Z from world Z.
     */
    public static double chunkZ(double wz) {
        return floor(wz / 16.);
    }
}