package xyz.upperlevel.openverse.util.math;

import lombok.EqualsAndHashCode;
import org.joml.*;

@EqualsAndHashCode
public class Aabb3f {
    public static final Aabb3f ZERO = new Aabb3f(0, 0, 0, 0, 0, 0);
    public static final Aabb3f INFINITY = new Aabb3f(
            Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY,
            Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY
    );

    public final float minX, minY, minZ;
    public final float maxX, maxY, maxZ;

    public Aabb3f(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Aabb3f(Vector3f min, Vector3f max) {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public boolean inside(float x, float y, float z) {
        return x >= minX && y >= minY && z >= minZ && x <= maxX && y <= maxY && z <= maxZ;
    }

    public boolean inside(Vector3fc point) {
        return inside(point.x(), point.y(), point.z());
    }

    public boolean inside(Aabb3f aabb) {
        return aabb.minX >= minX && aabb.minY >= minY && aabb.minZ >= minZ && aabb.maxX <= maxX && aabb.maxY <= maxY && aabb.maxZ <= maxZ;
    }

    public boolean intersectSphere(float centerX, float centerY, float centerZ, float radiusSquared) {
        return Intersectiond.testAabSphere(minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ, radiusSquared);
    }

    public boolean intersect(Aabb3f other) {
        return  this.maxX > other.minX &&
                this.maxY > other.minY &&
                this.maxZ > other.minZ &&
                this.minX < other.maxX &&
                this.minY < other.maxY &&
                this.minZ < other.maxZ;
    }

    public Aabb3f grow(float x, float y, float z) {
        return new Aabb3f(
                x < 0 ? minX + x : minX,
                y < 0 ? minY + y : minY,
                z < 0 ? minZ + z : minZ,
                x > 0 ? maxX + x : maxX,
                y > 0 ? maxY + y : maxY,
                z > 0 ? maxZ + z : maxZ
        );
    }

    public Aabb3f shrink(float x, float y, float z) {
        return new Aabb3f(
                x > 0 ? minX + x : minX,
                y > 0 ? minY + y : minY,
                z > 0 ? minZ + z : minZ,
                x < 0 ? maxX + x : maxX,
                y < 0 ? maxY + y : maxY,
                z < 0 ? maxZ + z : maxZ
        );
    }

    public Aabb3f union(Aabb3f other) {
        return new Aabb3f(
                this.minX > other.minX ? this.minX : other.minX,
                this.minY > other.minY ? this.minY : other.minY,
                this.minZ > other.minZ ? this.minZ : other.minZ,
                this.maxX < other.maxX ? this.maxX : other.maxX,
                this.maxY < other.maxY ? this.maxY : other.maxY,
                this.maxZ < other.maxZ ? this.maxZ : other.maxZ
        );
    }

    public Aabb3f translate(float x, float y, float z) {
        return new Aabb3f(
                this.minX + x,
                this.minY + y,
                this.minZ + z,
                this.maxX + x,
                this.maxY + y,
                this.maxZ + z
        );
    }

    public Aabb3f translate(Vector3f vec) {
        return translate(vec.x, vec.y, vec.z);
    }
}
