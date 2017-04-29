package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Vector3d;

/**
 * A location a class that identify a position somewhere in a world.
 * @param <W> A world may be client-side or server-side
 */
public class Location<W extends World> {

    @Getter
    @Setter
    @NonNull
    private W world;

    @Getter
    @Setter
    private double x, y, z, yaw, pitch;

    public Location(W world) {
        this.world  = world;
    }

    public Location(W world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(W world, double x, double y, double z, double yaw, double pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(Location<W> location) {
        world = location.world;
        x = location.x;
        y = location.y;
        z = location.z;
        yaw = location.yaw;
        pitch = location.pitch;
    }


    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(double x, double y, double z, double yaw, double pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void set(Location other) {
        x = other.getX();
        y = other.getY();
        z = other.getZ();
        yaw = other.getYaw();
        pitch = other.getPitch();
    }


    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Location location) {
        x += location.getX();
        y += location.getY();
        z += location.getZ();
    }

    public void add(Location location, Location dest) {
        dest.set(
                x + location.getX(),
                y + location.getY(),
                z + location.getZ()
        );
    }


    public void sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void sub(Location location) {
        x -= location.getX();
        y -= location.getY();
        z -= location.getZ();
    }

    public void sub(Location location, Location dest) {
        dest.set(
                x - location.getX(),
                y - location.getY(),
                z - location.getZ()
        );
    }


    public void mul(double m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
    }

    public void mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    public void mul(Location location) {
        this.x *= location.getX();
        this.y *= location.getY();
        this.z *= location.getZ();
    }

    public void mul(Location location, Location dest) {
        dest.set(
                x * location.getX(),
                y * location.getY(),
                z * location.getZ()
        );
    }

    public void mul(double m, Location dest) {
        dest.set(
                x * m,
                y * m,
                z * m
        );
    }


    public void div(double m) {
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }

    public void div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
    }

    public void div(Location location) {
        this.x /= location.getX();
        this.y /= location.getY();
        this.z /= location.getZ();
    }

    public void div(double m, Location dest) {
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }

    public void div(Location location, Location dest) {
        dest.set(
                x / location.getX(),
                y / location.getY(),
                z / location.getZ()
        );
    }


    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSq() {
        return x * x + y * y + z * z;
    }


    public double distance(Location location) {
        double dx = x - location.getX(),
                dy = y - location.getY(),
                dz = z - location.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSq(Location location) {
        double dx = x - location.getX(),
                dy = y - location.getY(),
                dz = z - location.getZ();
        return dx * dx + dy * dy + dz * dz;
    }


    public void normalize() {
        double m = (double) length();
        this.x /= m;
        this.y /= m;
        this.z /= m;
    }

    public void normalize(Location dest) {
        double m = (double) length();
        dest.set(
                x / m,
                y / m,
                z / m
        );
    }


    public Vector3d toVector() {
        return new Vector3d(x, y, z);
    }
}
