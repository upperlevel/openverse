package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Vector3f;

@Accessors(fluent = true)
public class Location {

    @Getter
    @Setter
    public float x, y, z, yaw, pitch;

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
        x = o.x();
        y = o.y();
        z = o.z();
        yaw = o.yaw();
        pitch = o.pitch();
    }


    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Location loc) {
        x += loc.x();
        y += loc.y();
        z += loc.z();
    }

    public void add(Location loc, Location dest) {
        dest.set(
                x + loc.x(),
                y + loc.y(),
                z + loc.z()
        );
    }


    public void sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(Location loc) {
        x -= loc.x();
        y -= loc.y();
        z -= loc.z();
    }

    public void sub(Location loc, Location dest) {
        dest.set(
                x - loc.x(),
                y - loc.y(),
                z - loc.z()
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
        this.x *= loc.x();
        this.y *= loc.y();
        this.z *= loc.z();
    }

    public void mul(Location loc, Location dest) {
        dest.set(
                x * loc.x(),
                y * loc.y(),
                z * loc.z()
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
        this.x /= loc.x();
        this.y /= loc.y();
        this.z /= loc.z();
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
                x / loc.x(),
                y / loc.y(),
                z / loc.z()
        );
    }


    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSq() {
        return x * x + y * y + z * z;
    }


    public double distance(Location loc) {
        float dx = x - loc.x(),
                dy = y - loc.y(),
                dz = z - loc.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSq(Location loc) {
        float dx = x - loc.x(),
                dy = y - loc.y(),
                dz = z - loc.z();
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
