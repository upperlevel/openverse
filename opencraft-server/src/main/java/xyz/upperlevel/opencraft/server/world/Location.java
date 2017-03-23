package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3f;

public class Location {

    @Getter
    @Setter
    private float x, y, z, yaw, pitch;

    public Location() {
    }

    public Location(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
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
        x = o.getX();
        y = o.getY();
        z = o.getZ();
        yaw = o.getYaw();
        pitch = o.getPitch();
    }


    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Location loc) {
        x += loc.getX();
        y += loc.getY();
        z += loc.getZ();
    }

    public void add(Location loc, Location dest) {
        dest.set(
                x + loc.getX(),
                y + loc.getY(),
                z + loc.getZ()
        );
    }


    public void sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(Location loc) {
        x -= loc.getX();
        y -= loc.getY();
        z -= loc.getZ();
    }

    public void sub(Location loc, Location dest) {
        dest.set(
                x - loc.getX(),
                y - loc.getY(),
                z - loc.getZ()
        );
    }


    public void mul(float m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
    }

    public void mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
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

    public void mul(float m, Location dest) {
        dest.set(
                x * m,
                y * m,
                z * m
        );
    }


    public void div(float m) {
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }

    public void div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
    }

    public void div(Location loc) {
        this.x /= loc.getX();
        this.y /= loc.getY();
        this.z /= loc.getZ();
    }

    public void div(float m, Location dest) {
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }

    public void div(Location loc, Location dest) {
        dest.set(
                x / loc.getX(),
                y / loc.getY(),
                z / loc.getZ()
        );
    }


    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSq() {
        return x * x + y * y + z * z;
    }


    public double distance(Location loc) {
        float dx = x - loc.getX(),
                dy = y - loc.getY(),
                dz = z - loc.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSq(Location loc) {
        float dx = x - loc.getX(),
                dy = y - loc.getY(),
                dz = z - loc.getZ();
        return dx * dx + dy * dy + dz * dz;
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


    public Vector3f toVector() {
        return new Vector3f(x, y, z);
    }
}
