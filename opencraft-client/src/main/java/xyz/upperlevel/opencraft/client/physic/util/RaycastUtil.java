package xyz.upperlevel.opencraft.client.physic.util;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3d;
import xyz.upperlevel.opencraft.client.physic.util.RaycastBlock.Face;

import static java.lang.Math.floor;

public class RaycastUtil {

    public static Vector3d getIntersection(double sx, double sy, double sz,
                                           double ex, double ey, double ez,
                                           int bx, int by, int bz,
                                           @NonNull Face face) {
        switch (face) {
            case LEFT:
            case RIGHT: {
                double x = (face == Face.RIGHT ? 1 : 0) + bx;
                double m = (x - sx) / (ex - sx);
                double y = m * (ey - sy) + sy;
                double z = m * (ez - sz) + sz;
                return new Vector3d(x, y, z);
            }
            case DOWN:
            case UP: {
                double y = (face == Face.UP ? 1 : 0) + by;
                double m = (y - sy) / (ey - sy);
                double x = m * (ex - sx) + sx;
                double z = m * (ez - sz) + sz;
                return new Vector3d(x, y, z);
            }
            case FRONT:
            case BACK: {
                double z = (face == Face.FRONT ? 1 : 0) + bz;
                double m = (z - sz) / (ez - sz);
                double y = m * (ey - sy) + sy;
                double x = m * (ex - sx) + sx;
                return new Vector3d(x, y, z);
            }
        }
        throw new IllegalStateException();
    }

/*
    // http://www.cse.yorku.ca/~amana/research/grid.pdf
    public static void line3d(double startX, double startY, double startZ, double endX, double endY, double endZ, IntersectionHandler callback) {
        // block's coordinates
        int rx = (int) floor(startX);
        int rex = (int) floor(endX);
        int ry = (int) floor(startY);
        int rey = (int) floor(endY);
        int rz = (int) floor(startZ);
        int rez = (int) floor(endZ);


        if (rx == rex && ry == rey && rz == rez && callback != null) {
            callback.onIntersect(rx, ry, rz, null);
            return;
        }

        double dx = endX - startX;
        double dy = endY - startY;
        double dz = endZ - startZ;

        int stepX = (int) Math.signum(dx);
        int stepY = (int) Math.signum(dy);
        int stepZ = (int) Math.signum(dz);

        double tMaxX = dx == 0 ? Double.POSITIVE_INFINITY : (forceCeil(startX) - startX) / dx * stepX;
        double tMaxY = dy == 0 ? Double.POSITIVE_INFINITY : (forceCeil(startY) - startY) / dy * stepY;
        double tMaxZ = dz == 0 ? Double.POSITIVE_INFINITY : (forceCeil(startZ) - startZ) / dz * stepZ;

        double tDeltaX = tMaxX * dx * stepX;
        double tDeltaY = tMaxY * dy * stepY;
        double tDeltaZ = tMaxZ * dz * stepZ;

        final Face faceX = stepX > 0 ? Face.LEFT : Face.RIGHT;
        final Face faceY = stepY > 0 ? Face.DOWN : Face.UP;
        final Face faceZ = stepZ > 0 ? Face.FRONT : Face.BACK;

        if (callback != null)
            callback.onIntersect(rx, ry, rz, null);

        Face face;
        do {
            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    tMaxX += tDeltaX;
                    startX += stepX;
                    rx += stepX;
                    face = faceX;
                } else {
                    tMaxZ += tDeltaZ;
                    startZ += stepZ;
                    rz += stepZ;
                    face = faceZ;
                }
            } else {
                if (tMaxY < tMaxZ) {
                    tMaxY += tDeltaY;
                    startY += stepY;
                    ry += stepY;
                    face = faceY;
                } else {
                    tMaxZ += tDeltaZ;
                    startZ += stepZ;
                    rz += stepZ;
                    face = faceZ;
                }
            }
        } while (!callback.onIntersect(rx, ry, rz, face) && !(rx == rex && ry == rey && rz == rez));
    }*/
}
