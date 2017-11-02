package xyz.upperlevel.openverse.util.math;

import lombok.EqualsAndHashCode;
import org.joml.Intersectiond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import static java.lang.Math.max;
import static java.lang.Math.min;

@EqualsAndHashCode
public class Aabb3d {
    public static final Aabb3d ZERO = new Aabb3d(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    public static final Aabb3d INFINITY = new Aabb3d(
            Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY
    );

    public final double minX, minY, minZ;
    public final double maxX, maxY, maxZ;

    public Aabb3d(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Aabb3d(Vector3d min, Vector3d max) {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public boolean inside(double x, double y, double z) {
        return x >= minX && y >= minY && z >= minZ && x <= maxX && y <= maxY && z <= maxZ;
    }

    public boolean inside(Vector3dc point) {
        return inside(point.x(), point.y(), point.z());
    }

    public boolean inside(Aabb3d aabb) {
        return aabb.minX >= minX && aabb.minY >= minY && aabb.minZ >= minZ && aabb.maxX <= maxX && aabb.maxY <= maxY && aabb.maxZ <= maxZ;
    }

    public boolean intersectSphere(double centerX, double centerY, double centerZ, double radiusSquared) {
        return Intersectiond.testAabSphere(minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ, radiusSquared);
    }

    public boolean intersect(Aabb3d other) {
        return  this.maxX > other.minX &&
                this.maxY > other.minY &&
                this.maxZ > other.minZ &&
                this.minX < other.maxX &&
                this.minY < other.maxY &&
                this.minZ < other.maxZ;
    }

    public Aabb3d grow(double x, double y, double z) {
        return new Aabb3d(
                x < 0 ? minX + x : minX,
                y < 0 ? minY + y : minY,
                z < 0 ? minZ + z : minZ,

                x > 0 ? maxX + x : maxX,
                y > 0 ? maxY + y : maxY,
                z > 0 ? maxZ + z : maxZ
        );
    }

    public Aabb3d shrink(double x, double y, double z) {
        return new Aabb3d(
                x > 0 ? minX + x : minX,
                y > 0 ? minY + y : minY,
                z > 0 ? minZ + z : minZ,
                x < 0 ? maxX + x : maxX,
                y < 0 ? maxY + y : maxY,
                z < 0 ? maxZ + z : maxZ
        );
    }

    public Aabb3d union(Aabb3d other) {
        return new Aabb3d(
                this.minX > other.minX ? this.minX : other.minX,
                this.minY > other.minY ? this.minY : other.minY,
                this.minZ > other.minZ ? this.minZ : other.minZ,
                this.maxX < other.maxX ? this.maxX : other.maxX,
                this.maxY < other.maxY ? this.maxY : other.maxY,
                this.maxZ < other.maxZ ? this.maxZ : other.maxZ
        );
    }

    public Aabb3d translate(double x, double y, double z) {
        return new Aabb3d(
                this.minX + x,
                this.minY + y,
                this.minZ + z,
                this.maxX + x,
                this.maxY + y,
                this.maxZ + z
        );
    }

    public Aabb3d translate(Vector3d vec) {
        return translate(vec.x, vec.y, vec.z);
    }

    /**
     * Returns how much are the two boxes overlapping in the x axis
     * @param other the other box
     * @param vel the velocity with which the block was intersected (used to calculate the face)
     * @return the intersected distance (or vel if they don't intersect)
     */
    public double getOverlapX(Aabb3d other, double vel) {
        //If intersects in the y and the z axis
        if (    this.maxY > other.minY &&
                this.maxZ > other.minZ &&
                this.minY < other.maxY &&
                this.minZ < other.maxZ) {
            //See in which face it entered
            if (vel > 0 && this.minX >= other.maxX) {
                return this.minX - other.maxX;
            } else if (vel < 0 && this.maxX <= other.minX) {
                return this.maxX - other.minX;
            }
        }
        return vel;
    }

    /**
     * Returns how much are the two boxes overlapping in the y axis
     * @param other the other box
     * @param vel the velocity with which the block was intersected (used to calculate the face)
     * @return the intersected distance (or vel if they don't intersect)
     */
    public double getOverlapY(Aabb3d other, double vel) {
        //If intersects in the x and the z axis
        if (    this.maxX > other.minX &&
                this.maxZ > other.minZ &&
                this.minX < other.maxX &&
                this.minZ < other.maxZ){
            //See in which face it entered
            if (vel > 0 && this.minY >= other.maxY) {
                return this.minY - other.maxY;
            } else if (vel < 0 && this.maxY <= other.minY) {
                return this.maxY - other.minY;
            }
        }
        return vel;
    }

    /**
     * Returns how much are the two boxes overlapping in the y axis
     * @param other the other box
     * @param vel the velocity with which the block was intersected (used to calculate the face)
     * @return the intersected distance (or vel if they don't intersect)
     */
    public double getOverlapZ(Aabb3d other, double vel) {
        //If intersects in the x and the y axis
        if (    this.maxX > other.minX &&
                this.maxY > other.minY &&
                this.minX < other.maxX &&
                this.minY < other.maxY) {
            //See in which face it entered
            if (vel > 0 && this.minZ >= other.maxZ) {
                return this.minZ - other.maxZ;
            } else if (vel < 0 && this.maxZ <= other.minZ) {
                return this.maxZ - other.minZ;
            }
        }
        return vel;
    }

    public boolean rayTest(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        // Using the slabs implementation (https://gamedev.stackexchange.com/questions/18436/most-efficient-aabb-vs-ray-collision-algorithms)
        double dX = endX - startX;
        double dY = endY - startY;
        double dZ = endZ - startZ;

        double tx1 = (minX - startX) / dX;
        double tx2 = (maxX - startX) / dX;

        double ty1 = (minY - startY) / dY;
        double ty2 = (maxY - startY) / dY;

        double tz1 = (minZ - startZ) / dZ;
        double tz2 = (maxZ - startZ) / dZ;

        double tmin = max(max(min(tx1, tx2), min(ty1, ty2)), min(tz1, tz2));
        double tmax = min(min(max(tx1, tx2), max(ty1, ty2)), max(tz1, tz2));

        if (tmax < 0) {//Intersecting behind
            return false;
        }

        if (tmin >= tmax) {// Not intersecting
            return false;
        }
        //Intersecting
        return true;
    }

    public static void main(String[] args) {
        new Aabb3d(2, 2, 2, 4, 4, 4).rayTest(1, 1, 1, 5, 5, 5);
    }

    @Override
    public String toString() {
        return minX + ", " + minY + ", " + minZ + " -> " + maxX + ", " + maxY + ", " + maxZ;
    }
}
