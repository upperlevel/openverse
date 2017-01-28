package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.World;

import java.util.Objects;

public class WorldViewer {

    @Getter
    @NonNull
    private World world;

    @Getter
    public double x = 0, y = 0, z = 0;

    @Getter
    private float yaw = 0, pitch = 0;

    @Getter
    public float fov = 50f;

    @Getter
    public final RenderArea renderArea;

    public WorldViewer(World world) {
        this(world, 0, 0, 0);
    }

    public WorldViewer(World world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    public WorldViewer(World world, double x, double y, double z, float yaw, float pitch) {
        Objects.requireNonNull(world, "World cannot be null.");
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;

        renderArea = new RenderArea(this);
    }

    public boolean isInFrustum(Block block) {
        return true;
    }

    public void setWorld(World world) {
        Objects.requireNonNull(world, "World cannot be null.");
        this.world = world;
        // todo update buffer
    }

    public void setX(double x) {
        this.x = x;
        // todo update buffer
    }

    public void setY(double y) {
        this.y = y;
        // todo update buffer
    }

    public void setZ(double z) {
        this.z = z;
        // todo update buffer
    }

    public void setPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        // todo update buffer
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        // todo update buffer
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        // todo update buffer
    }

    public void setRotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        // todo update buffer
    }

    public void setFov(float fov) {
        this.fov = fov;
        // todo update buffer
    }

    public Block getBlock() {
        return world.getBlock((int) x, (int) y, (int) z);
    }

    public Chunk getChunk() {
        return getBlock().getChunk();
    }
}