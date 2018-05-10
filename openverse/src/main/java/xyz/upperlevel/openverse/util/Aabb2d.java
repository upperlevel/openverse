package xyz.upperlevel.openverse.util;

import lombok.Getter;

@Getter
public class Aabb2d {
    public double minX, minY;
    public double maxX, maxY;

    public Aabb2d() {
    }

    public Aabb2d(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Tests if the point is inside this AABB.
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @return {@code true} if inside, {@code false} if not.
     */
    public boolean testPoint(double x, double y) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    /**
     * Checks if the AABB has at least one corner inside this AABB.
     *
     * @param aabb the AABB
     * @return {@code true} if collides, {@code false} if not.
     */
    public boolean isColliding(Aabb2d aabb) {
        return testPoint(aabb.minX, aabb.minY) || testPoint(aabb.maxX, aabb.maxY);
    }

    /**
     * Checks if the AABB has both the corners inside this AABB.
     *
     * @param aabb the AABB
     * @return {@code true} if inside, {@code false} if not (also if it collides).
     */
    public boolean isInside(Aabb2d aabb) {
        return testPoint(aabb.minX, aabb.minY) && testPoint(aabb.maxX, aabb.maxY);
    }
}
