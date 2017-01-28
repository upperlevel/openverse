package xyz.upperlevel.opencraft.render;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.World;

import java.util.Objects;

@RequiredArgsConstructor
public class WorldViewer {

    @Getter
    @NonNull
    private World world;

    @Getter
    @Setter
    public double x = 0, y = 0, z = 0;

    @Getter
    @Setter
    public double yaw = 0, pitch = 0;

    @Getter
    @Setter
    public float fov = 50f;

    @Getter
    public final RenderArea renderArea;

    public WorldViewer(World world) {
        this(world, 0, 0, 0);
    }

    public WorldViewer(World world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    public WorldViewer(World world, double x, double y, double z, double yaw, double pitch) {
        Objects.requireNonNull(world, "World cannot be null.");
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

    public void setPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        renderArea.translateBuffer(
                x - (int) this.x,
                y - (int) this.y,
                z - (int) this.z
        );
    }

    public void setWorld(World world) {
        Objects.requireNonNull(world, "World cannot be null.");
        this.world = world;
        renderArea.setupBuffer(); // setup buffer when world change
    }

    public Block getBlock() {
        return world.getBlock((int) x, (int) y, (int) z);
    }

    public Chunk getChunk() {
        return getBlock().getChunk();
    }
}