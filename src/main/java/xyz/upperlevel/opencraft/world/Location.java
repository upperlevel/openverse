package xyz.upperlevel.opencraft.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.ulge.util.math.VecOperable;

@AllArgsConstructor
public class Location implements VecOperable<Location> {

    @Getter
    @Setter
    @NonNull
    private World world;

    @Getter
    @Setter
    public double x = 0, y = 0, z = 0;

    public Location(World world) {
        this.world = world;
    }
    public Chunk getChunk() {
        return world.getChunk(getChunkX(), getChunkY(), getChunkZ());
    }

    public int getChunkX() {
        return world.getChunkX(x);
    }

    public int getChunkY() {
        return world.getChunkY(y);
    }

    public int getChunkZ() {
        return world.getChunkZ(z);
    }

    public double asChunkX() {
        return world.toChunkX(x);
    }

    public double asChunkY() {
        return world.toChunkY(y);
    }

    public double asChunkZ() {
        return world.toChunkZ(z);
    }

    public Block getBlock() {
        return world.getBlock(getBlockX(), getBlockY(), getBlockZ());
    }

    public boolean isBlockLocation() {
        return ((int) x) == x && ((int) y) == y && ((int) z) == z;
    }

    public int getBlockX() {
        return (int) x;
    }

    public int getBlockY() {
        return (int) y;
    }

    public int getBlockZ() {
        return (int) z;
    }

    @Override
    public int hashCode() {
        return isBlockLocation() ? (int) asChunkX() << 8 | (int) asChunkY() << 4 | (int) asChunkZ() : super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Location) {
            Location location = (Location) object;
            return world.equals(location.world) &&
                    x == location.x &&
                    y == location.y &&
                    z == location.z;
        }
        return super.equals(object);
    }

    public Location set(Location other) {
        world = other.world;
        x = other.x;
        y = other.y;
        z = other.z;
        return this;
    }

    @Override
    public Location add(Location other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    @Override
    public Location sub(Location other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    @Override
    public Location mul(Location other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    @Override
    public Location div(Location other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    @Override
    public Location zero() {
        x = 0;
        y = 0;
        z = 0;
        return this;
    }

    @Override
    public Location normalize() {
        double invLen = 1. / length();
        x *= invLen;
        y *= invLen;
        z *= invLen;
        return this;
    }

    @Override
    public Location copy() {
        return new Location(
                world,
                x,
                y,
                z
        );
    }

    @Override
    public double lengthSquared() {
        return x * x + y * y + z * z;
    }
}
