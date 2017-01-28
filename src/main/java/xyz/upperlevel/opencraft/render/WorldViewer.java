package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.World;

public class WorldViewer {

    // position

    @Getter
    @Setter
    @NonNull
    private World world;

    @Getter
    @Setter
    private double x = 0, y = 0, z = 0;

    @Getter
    @Setter
    private float yaw = 0, pitch = 0;

    // rendering info

    @Getter
    @Setter
    private float fov = 50f;

    @Getter
    private final RenderArea renderArea;

    public WorldViewer(World world) {
        this(world, 0, 0, 0);
    }

    public WorldViewer(World world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    public WorldViewer(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;

        renderArea = new RenderArea(this);
    }

    public boolean isInFrustum(Block block) {
        // todo checks if given block is in frustum
        return true;
    }

    public int getRenderDistance() {
        return renderArea.getRenderDistance();
    }

    public void setRenderDistance(int renderDistance) {
        if (renderDistance < 0)
            throw new IllegalArgumentException("render distance cannot be negative");
        renderArea.setRenderDistance(renderDistance);
    }

    /**
     * Updates render area on world viewer move.
     */
    public void update() {
        renderArea.updateBuffer();
    }

    public Chunk getChunk() {
        return world.getChunk(
                getChunkX(),
                getChunkY(),
                getChunkZ()
        );
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
}