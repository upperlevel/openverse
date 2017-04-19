package xyz.upperlevel.openverse.physic;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class Box {

    @Getter
    @Setter
    public double x, y, z;

    @Getter
    @Setter
    public double width, height, depth;

    public Box() {
    }

    public Box(double x, double y, double z, double width, double height, double depth) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public double minX() {
        return x;
    }

    public double minY() {
        return y;
    }

    public double minZ() {
        return z;
    }

    public double maxX() {
        return x + width;
    }

    public double maxY() {
        return y + height;
    }

    public double maxZ() {
        return z + depth;
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public boolean intersect(Box box) {
        return isIn(box.x, box.y, box.z) || isIn(box.maxX(), box.maxY(), box.maxZ());
    }

    public boolean isIn(double x, double y, double z) {
        return this.x < x && x < maxX() &&
                this.y < y && y < maxY() &&
                this.z < z && z < maxZ();
    }

    public boolean isIn(Box box) {
        return isIn(box.x, box.y, box.z) && isIn(box.maxX(), box.maxY(), box.maxZ());
    }

    public Box copy() {
        return new Box(x, y, z, width, height, depth);
    }
}