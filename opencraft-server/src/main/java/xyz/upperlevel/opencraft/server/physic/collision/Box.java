package xyz.upperlevel.opencraft.server.physic.collision;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3f;

public class Box {

    @Getter
    private Vector3f pos, dim;

    public Box() {
    }

    public Box(@NonNull Vector3f pos, @NonNull Vector3f dim) {
        this.pos = pos;
        this.dim = dim;
    }

    public Box(float x, float y, float z, float w, float h, float d) {
        this(new Vector3f(x, y, z),
             new Vector3f(w, h, d));
    }

    public static void main(String[] a) {
        Box b1 = new Box(0,0,0,1,1,1);
        Box b2 = new Box(0,0.9f,0,1,1,1);

        System.out.println("b1 max: " + b1.getMax());
        System.out.println("b2 max: " + b2.getMax());

        System.out.println("intersect b1 with b2? " + b1.intersect(b2));
        System.out.println("is b2 in b1? " + b1.isIn(b2));
    }

    public Vector3f getMin() {
        return pos;
    }

    public Vector3f getMax() {
        return pos.add(dim, new Vector3f());
    }

    public boolean isIn(float x, float y, float z) {
        Vector3f max = getMax();
        return pos.x < x && x < max.x &&
               pos.y < y && y < max.y &&
               pos.z < z && z < max.z;
    }

    public boolean isIn(Vector3f p) {
        return isIn(p.x, p.y, p.z);
    }

    public boolean intersect(Box box) {
        return isIn(box.pos) || isIn(box.getMax());
    }

    public boolean isIn(Box box) {
        return isIn(box.pos) && isIn(box.getMax());
    }

    public Box copy() {
        return new Box(pos, dim);
    }
}