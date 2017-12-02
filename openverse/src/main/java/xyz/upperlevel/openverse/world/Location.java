package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3d;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.world.chunk.Block;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import static java.lang.Math.floor;
import static xyz.upperlevel.openverse.util.math.MathUtil.normalizeAngle;

public class Location {

    @Getter
    @Setter
    @NonNull
    private World world;

    @Getter
    @Setter
    private double x, y, z;

    @Getter
    private double yaw, pitch;

    public Location(World world) {
        this.world = world;
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
        this.yaw = normalizeAngle(yaw);
        this.pitch = normalizeAngle(pitch);
    }

    public Location(Location location) {
        world = location.world;
        x = location.x;
        y = location.y;
        z = location.z;
        yaw = location.yaw;
        pitch = location.pitch;
    }

    public Chunk getChunk() {
        return world.getChunkFromBlock((int)floor(x), (int)floor(y), (int)floor(z));
    }

    public Block getBlock() {
        return world.getBlock(x, y, z);
    }

    public void setYaw(double yaw) {
        this.yaw = normalizeAngle(yaw);
    }

    public void setPitch(double pitch) {
        this.pitch = normalizeAngle(pitch);
    }

    public Location set(double x, double y, double z, double yaw, double pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = normalizeAngle(yaw);
        this.pitch = normalizeAngle(pitch);
        return this;
    }

    public Location set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Location set(Location other) {
        x = other.getX();
        y = other.getY();
        z = other.getZ();
        yaw = other.getYaw();
        pitch = other.getPitch();
        return this;
    }

    public Location setRotation(double yaw, double pitch) {
        this.yaw = normalizeAngle(yaw);
        this.pitch = normalizeAngle(pitch);
        return this;
    }

    public Location add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Location add(Vector3f vector) {
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }

    public Location add(Location location) {
        x += location.x;
        y += location.y;
        z += location.z;
        return this;
    }

    public Location add(Location location, Location dest) {
        dest.set(
                x + location.getX(),
                y + location.getY(),
                z + location.getZ()
        );
        return dest;
    }

    public Location sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Location sub(Location location) {
        x -= location.getX();
        y -= location.getY();
        z -= location.getZ();
        return this;
    }

    public Location sub(Location location, Location dest) {
        dest.set(
                x - location.getX(),
                y - location.getY(),
                z - location.getZ()
        );
        return dest;
    }


    public Location mul(double m) {
        this.x *= m;
        this.y *= m;
        this.z *= m;
        return this;
    }

    public Location mul(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Location mul(Location location) {
        this.x *= location.getX();
        this.y *= location.getY();
        this.z *= location.getZ();
        return this;
    }

    public Location mul(Location location, Location dest) {
        dest.set(
                x * location.getX(),
                y * location.getY(),
                z * location.getZ()
        );
        return dest;
    }

    public Location mul(double m, Location dest) {
        dest.set(
                x * m,
                y * m,
                z * m
        );
        return dest;
    }


    public Location div(double m) {
        this.x /= m;
        this.y /= m;
        this.z /= m;
        return this;
    }

    public Location div(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Location div(Location location) {
        this.x /= location.getX();
        this.y /= location.getY();
        this.z /= location.getZ();
        return this;
    }

    public Location div(double m, Location dest) {
        dest.set(
                x / m,
                y / m,
                z / m
        );
        return dest;
    }

    public Location div(Location location, Location dest) {
        dest.set(
                x / location.getX(),
                y / location.getY(),
                z / location.getZ()
        );
        return dest;
    }


    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }


    public double distance(Location location) {
        double  dx = x - location.getX(),
                dy = y - location.getY(),
                dz = z - location.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double distanceSquared(Location location) {
        double  dx = x - location.getX(),
                dy = y - location.getY(),
                dz = z - location.getZ();
        return dx * dx + dy * dy + dz * dz;
    }


    public Location normalize() {
        double m = length();
        this.x /= m;
        this.y /= m;
        this.z /= m;
        return this;
    }

    public Location normalize(Location dest) {
        double m = length();
        dest.set(
                x / m,
                y / m,
                z / m
        );
        return dest;
    }

    public Vector3d getLook() {
        double cosYaw = Math.cos(Math.toRadians(-yaw) + Math.PI);
        double sinYaw = Math.sin(Math.toRadians(-yaw) + Math.PI);
        double cosPitch = Math.cos(Math.toRadians(-pitch));
        double sinPitch = Math.sin(Math.toRadians(-pitch));
        return new Vector3d(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
    }

    public Location copy() {
        return new Location(this);
    }


    public Vector3d toVector() {
        return new Vector3d(x, y, z);
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " +  z;
    }

    public String toStringComplete() {
        return toString() + ", " + yaw + ", " + pitch;
    }
}
