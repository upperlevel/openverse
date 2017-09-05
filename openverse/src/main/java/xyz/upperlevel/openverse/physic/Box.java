package xyz.upperlevel.openverse.physic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Vector3f;

import static java.lang.Math.abs;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    public double minX, minY, minZ;
    public double maxX, maxY, maxZ;

    public Vector3f getPosition() {
        return new Vector3f(
                (float) minX,
                (float) minY,
                (float) minZ
        );
    }

    public double getWidth() {
        return maxX - minX;
    }

    public double getHeight() {
        return maxY - minY;
    }

    public double getLength() {
        return maxZ - minZ;
    }

    public Vector3f getSize() {
        return new Vector3f(
                (float) getWidth(),
                (float) getHeight(),
                (float) getLength()
        );
    }

    public void add(double x, double y, double z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;

        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }

    public boolean intersect(Box box) {
        return (minX <= box.maxX && maxX >= box.minX) &&
                (minY <= box.maxY && maxY >= box.minY) &&
                (minZ <= box.maxZ && maxZ >= box.minZ);
    }

    public boolean isIn(double x, double y, double z) {
        return (x >= minX && x <= maxX) &&
                (y >= minY && y <= maxY) &&
                (z >= minZ && z <= maxZ);
    }

    public Box copy() {
        return new Box(
                minX, minY, minZ,
                maxX, maxY, maxZ
        );
    }
}