package xyz.upperlevel.opencraft.server.physic.util;

import lombok.NonNull;
import org.joml.Vector3d;

import static xyz.upperlevel.opencraft.server.physic.util.PhysicalFace.*;

public class RaycastUtil {

    private RaycastUtil() {
    }

    public static Vector3d getIntersection(double sx, double sy, double sz,
                                           double ex, double ey, double ez,
                                           int bx, int by, int bz,
                                           @NonNull PhysicalFace face) {
        switch (face) {
            case LEFT:
            case RIGHT: {
                double x = (face == RIGHT ? 1 : 0) + bx;
                double m = (x - sx) / (ex - sx);
                double y = m * (ey - sy) + sy;
                double z = m * (ez - sz) + sz;
                return new Vector3d(x, y, z);
            }
            case DOWN:
            case UP: {
                double y = (face == UP ? 1 : 0) + by;
                double m = (y - sy) / (ey - sy);
                double x = m * (ex - sx) + sx;
                double z = m * (ez - sz) + sz;
                return new Vector3d(x, y, z);
            }
            case FRONT:
            case BACK: {
                double z = (face == FRONT ? 1 : 0) + bz;
                double m = (z - sz) / (ez - sz);
                double y = m * (ey - sy) + sy;
                double x = m * (ex - sx) + sx;
                return new Vector3d(x, y, z);
            }
        }
        throw new IllegalStateException();
    }
}
