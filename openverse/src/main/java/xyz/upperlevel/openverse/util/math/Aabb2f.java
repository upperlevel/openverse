package xyz.upperlevel.openverse.util.math;

import org.joml.*;

import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

public class Aabb2f {
    public static final Aabb2f ZERO = new Aabb2f(0, 0, 0, 0);
    public static final Aabb2f INFINITY = new Aabb2f(NEGATIVE_INFINITY, NEGATIVE_INFINITY, POSITIVE_INFINITY, POSITIVE_INFINITY);

    public final float minX, minY;
    public final float maxX, maxY;

    public Aabb2f(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Aabb2f(Vector2f min, Vector2f max) {
        this(min.x, min.y, max.x, max.y);
    }

    public boolean inside(float x, float y) {
        return x > minX && y > minY && x < maxX && y < maxY;
    }

    public boolean inside(Vector2fc point) {
        return inside(point.x(), point.y());
    }

    public boolean inside(Aabb2f aabb) {
        return aabb.minX > minX && aabb.minY > minY&& aabb.maxX < maxX && aabb.maxY < maxY;
    }

    public boolean intersect(Aabb2f other) {
        return  this.maxX > other.minX &&
                this.maxY > other.minY &&
                this.minX < other.maxX &&
                this.minY < other.maxY;
    }

    public Aabb2f grow(float x, float y) {
        return new Aabb2f(
                x < 0 ? minX + x : minX,
                y < 0 ? minY + y : minY,
                x > 0 ? maxX + x : maxX,
                y > 0 ? maxY + y : maxY
        );
    }

    public Aabb2f shrink(float x, float y) {
        return new Aabb2f(
                x > 0 ? minX + x : minX,
                y > 0 ? minY + y : minY,
                x < 0 ? maxX + x : maxX,
                y < 0 ? maxY + y : maxY
        );
    }

    public Aabb2f union(Aabb2f other) {
        return new Aabb2f(
                this.minX > other.minX ? this.minX : other.minX,
                this.minY > other.minY ? this.minY : other.minY,
                this.maxX < other.maxX ? this.maxX : other.maxX,
                this.maxY < other.maxY ? this.maxY : other.maxY
        );
    }

    public Aabb2f translate(float x, float y) {
        return new Aabb2f(
                this.minX + x,
                this.minY + y,
                this.maxX + x,
                this.maxY + y
        );
    }

    public Aabb2f translate(Vector2f vec) {
        return translate(vec.x, vec.y);
    }
}
