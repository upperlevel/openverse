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
        return chunk.getX() * 16 + cpx;
    }

    /**
     * Gets world Y from chunk relative Y.
     */
    public static double worldY(Chunk chunk, double cpy) {
        return chunk.getY() * 16 + cpy;
    }

    /**
     * Gets world Z from chunk relative Z.
     */
    public static double worldZ(Chunk chunk, double cpz) {
        return chunk.getZ() * 16 + cpz;
    }

    // ---

    private static double euclideanMod(double x, int y) {
        double r = abs(x) % abs(y);
        r *= Math.signum(x);
        r = (r + abs(y)) % abs(y);
        return r;
    }

    /**
     * Gets chunk relative X from world X.
     */
    public static double chunkPosX(double wx) {
        return euclideanMod(wx, 16);
    }

    /**
     * Gets chunk relative Y from world Y.
     */
    public static double chunkPosY(double wy) {
        return euclideanMod(wy, 16);
    }

    /**
     * Gets chunk relative Z from world Z.
     */
    public static double chunkPosZ(double wz) {
        return euclideanMod(wz, 16);
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