package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.joml.Vector3d;

@Accessors(fluent = true)
public class Location {

    @Getter
    @Setter
    @NonNull
    private World world;

    @Getter
    @Setter
    public double x, y, z, yaw, pitch;

    public Location() {
    }

    public Location(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(World world, double x, double y, double z, double yaw, double pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(Location loc) {
        world = loc.world;
        x = loc.x;
        y = loc.y;
        z = loc.z;
        yaw = loc.yaw;
        pitch = loc.pitch;
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

    public void set(Location o) {
        x = o.x();
        y = o.y();
        z = o.z();
        yaw = o.yaw();
        pitch = o.pitch();
    }


    public void add(double x, double y, double z) {
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


    public void sub(double x, double y, double z) {
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

    public void div(Location loc) {
        this.x /= loc.x();
        this.y /= loc.y();
        this.z /= loc.z();
    }

    public void div(double m, Location dest) {
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
        double dx = x - loc.x(),
                dy = y - loc.y(),
                dz = z - loc.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSq(Location loc) {
        double dx = x - loc.x(),
                dy = y - loc.y(),
                dz = z - loc.z();
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
