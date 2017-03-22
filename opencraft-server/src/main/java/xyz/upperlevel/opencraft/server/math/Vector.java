package xyz.upperlevel.opencraft.server.math;

import lombok.Getter;
import lombok.Setter;

public class Vector {
    @Getter
    @Setter
    private float x, y, z;

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector o) {
        this.x = o.x;
        this.y = o.y;
        this.z = o.z;
    }


    public void add(Vector loc) {
        this.x += loc.x;
        this.y += loc.y;
        this.z += loc.z;
    }

    public void add(Vector loc, Vector dest) {
        dest.set(
                x + loc.x,
                y + loc.y,
                z + loc.z
        );
    }

    public void add(float x, float y, float z, float yaw, float pitch) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }


    public void sub(Vector loc) {
        this.x -= loc.x;
        this.y -= loc.y;
        this.z -= loc.z;
    }

    public void sub(Vector loc, Vector dest) {
        dest.set(
                x - loc.x,
                y - loc.y,
                z - loc.z
        );
    }

    public void sub(float x, float y, float z, float yaw, float pitch) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }


    public void mul(Vector loc) {
        this.x *= loc.x;
        this.y *= loc.y;
        this.z *= loc.z;
    }

    public void mul(Vector loc, Vector dest) {
        dest.set(
                x * loc.x,
                y * loc.y,
                z * loc.z
        );
    }

    public void mul(float x, float y, float z, float yaw, float pitch) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    public void mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    public void mul(float m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
    }

    public void mul(float m, Vector dest) {
        dest.set(
                x * m,
                y * m,
                z * m
        );
    }



    public void div(Vector loc) {
        this.x /= loc.x;
        this.y /= loc.y;
        this.z /= loc.z;
    }

    public void div(Vector loc, Vector dest) {
        dest.set(
                x / loc.x,
                y / loc.y,
                z / loc.z
        );
    }

    public void div(float x, float y, float z, float yaw, float pitch) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
    }

    public void div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
    }

    public void div(float m) {
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }

    public void div(float m, Vector dest) {
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }


    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public double lengthSq() {
        return x*x + y*y + z*z;
    }

    public double distance(Vector loc) {
        float   dx = x - loc.x,
                dy = y - loc.y,
                dz = z - loc.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public double distanceSq(Vector loc) {
        float   dx = x - loc.x,
                dy = y - loc.y,
                dz = z - loc.z;
        return dx*dx + dy*dy + dz*dz;
    }

    public void normalize() {
        float m = (float) length();
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }

    public void normalize(Vector dest) {
        float m = (float) length();
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }
}
