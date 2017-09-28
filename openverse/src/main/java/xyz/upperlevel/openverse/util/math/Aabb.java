package xyz.upperlevel.openverse.util.math;

import lombok.EqualsAndHashCode;
import org.joml.Intersectiond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@EqualsAndHashCode
public class Aabb {
    public static final Aabb ZERO = new Aabb(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public static final Aabb INFINITY = new Aabb(
            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY
    );

    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public Aabb(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Aabb(Vector3d min, Vector3d max) {
        this(
                min.x,
                min.y,
                min.z,

                max.x,
                max.y,
                max.z
        );
    }

    public boolean inside(double x, double y, double z) {
        return x >= minX && y >= minY && z >= minZ && x <= maxX && y <= maxY && z <= maxZ;
    }

    public boolean inside(Vector3dc point) {
        return inside(point.x(), point.y(), point.z());
    }

    public boolean intersectSphere(double centerX, double centerY, double centerZ, double radiusSquared) {
        return Intersectiond.testAabSphere(minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ, radiusSquared);
    }

    public boolean intersect(Aabb other) {
        return  this.maxX >= other.minX &&
                this.maxY >= other.minY &&
                this.maxZ >= other.minZ &&
                this.minX <= other.maxX &&
                this.minY <= other.maxY &&
                this.minZ <= other.maxZ;
    }

    public Aabb grow(double x, double y, double z) {
        return new Aabb(
                x < 0 ? minX - x : minX,
                y < 0 ? minY - y : minY,
                z < 0 ? minZ - z : minZ,
                x > 0 ? maxX + x : maxX,
                y > 0 ? maxY + y : maxY,
                z > 0 ? maxZ + z : maxZ
        );
    }

    public Aabb shrink(double x, double y, double z) {
        return new Aabb(
                x > 0 ? minX + x : minX,
                y > 0 ? minY + y : minY,
                z > 0 ? minZ + z : minZ,
                x < 0 ? maxX - x : maxX,
                y < 0 ? maxY - y : maxY,
                z < 0 ? maxZ - z : maxZ
        );
    }

    public Aabb union(Aabb other) {
        return new Aabb(
                this.minX > other.minX ? this.minX : other.minX,
                this.minY > other.minY ? this.minY : other.minY,
                this.minZ > other.minZ ? this.minZ : other.minZ,
                this.maxX < other.maxX ? this.maxX : other.maxX,
                this.maxY < other.maxY ? this.maxY : other.maxY,
                this.maxZ < other.maxZ ? this.maxZ : other.maxZ
        );
    }

    public Aabb translate(double x, double y, double z) {
        return new Aabb(
                this.minX + x,
                this.minY + y,
                this.minZ + z,
                this.maxX + x,
                this.maxY + y,
                this.maxZ + z
        );
    }

    public Aabb translate(Vector3d vec) {
        return translate(vec.x, vec.y, vec.z);
    }
}
