package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.server.math.Vector;

public class Location {

    @Getter
    @Setter
    private float x, y, z, yaw, pitch;

    public Location(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0f;
        this.pitch = 0f;
    }

    public Location(Vector vec) {
        this(vec.getX(), vec.getY(), vec.getZ());
    }


    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void set(Location o) {
        this.x = o.getX();
        this.y = o.getY();
        this.z = o.getZ();
        this.yaw = o.getYaw();
        this.pitch = o.getPitch();
    }

    public void set(Vector o) {
        this.x = o.getX();
        this.y = o.getY();
        this.z = o.getZ();
    }


    public void add(Location loc) {
        this.x += loc.getX();
        this.y += loc.getY();
        this.z += loc.getZ();
    }

    public void add(Location loc, Location dest) {
        dest.set(
                x + loc.getX(),
                y + loc.getY(),
                z + loc.getZ()
        );
    }

    public void add(Vector loc) {
        this.x += loc.getX();
        this.y += loc.getY();
        this.z += loc.getZ();
    }

    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }


    public void sub(Location loc) {
        this.x -= loc.getX();
        this.y -= loc.getY();
        this.z -= loc.getZ();
    }

    public void sub(Location loc, Location dest) {
        dest.set(
                x - loc.getX(),
                y - loc.getY(),
                z - loc.getZ()
        );
    }

    public void sub(Vector loc) {
        this.x -= loc.getX();
        this.y -= loc.getY();
        this.z -= loc.getZ();
    }

    public void sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void mul(Location loc) {
        this.x *= loc.getX();
        this.y *= loc.getY();
        this.z *= loc.getZ();
    }

    public void mul(Location loc, Location dest) {
        dest.set(
                x * loc.getX(),
                y * loc.getY(),
                z * loc.getZ()
        );
    }

    public void mul(Vector loc) {
        this.x *= loc.getX();
        this.y *= loc.getY();
        this.z *= loc.getZ();
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

    public void mul(float m, Location dest) {
        dest.set(
                x * m,
                y * m,
                z * m
        );
    }

    public void mul(float m, Vector dest) {
        dest.set(
                x * m,
                y * m,
                z * m
        );
    }


    public void div(Location loc) {
        this.x /= loc.getX();
        this.y /= loc.getY();
        this.z /= loc.getZ();
    }

    public void div(Location loc, Location dest) {
        dest.set(
                x / loc.getX(),
                y / loc.getY(),
                z / loc.getZ()
        );
    }

    public void div(Vector loc) {
        this.x /= loc.getX();
        this.y /= loc.getY();
        this.z /= loc.getZ();
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

    public void div(float m, Location dest) {
        dest.set(
                x / m,
                y / m,
                z / m
        );
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

    public double distance(Location loc) {
        float   dx = x - loc.getX(),
                dy = y - loc.getY(),
                dz = z - loc.getZ();
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    public double distanceSq(Location loc) {
        float   dx = x - loc.getX(),
                dy = y - loc.getY(),
                dz = z - loc.getZ();
        return dx*dx + dy*dy + dz*dz;
    }


    public Matrix4f getOrientation() {
        Matrix4f result = new Matrix4f();
        //result.rotate((float) Math.toRadians(pitch), new Vector3f(1f, 0, 0));
        result.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1f, 0));
        return result;
    }

    public Vector3f getForward() {
        return getOrientation().invert(new Matrix4f()).transformDirection(new Vector3f(0f, 0f, -1f));
    }

    public Vector3f getBackward() {
        return getForward().mul(-1f);
    }

    public Vector3f getRight() {
        return getOrientation().invert(new Matrix4f()).transformDirection(new Vector3f(1f, 0, 0));
    }

    public Vector3f getLeft() {
        return getRight().mul(-1f);
    }

    public Vector3f getUp() {
        return new Vector3f(0, 1f, 0);
    }

    public Vector3f getDown() {
        return new Vector3f(0, -1f, 0);
    }


    public void normalize() {
        float m = (float) length();
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }

    public void normalize(Location dest) {
        float m = (float) length();
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }

    public void normalize(Vector dest) {
        float m = (float) length();
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

}
