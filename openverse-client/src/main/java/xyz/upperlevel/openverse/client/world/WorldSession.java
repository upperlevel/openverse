package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.render.entity.EntityViewRenderer;
import xyz.upperlevel.openverse.client.render.world.ChunkViewRenderer;

/**
 * Handles world renderers and changes.
 */
@Getter
public class WorldSession implements PacketListener {
    private ClientWorld world;
    private final ChunkViewRenderer chunkView;
    private final EntityViewRenderer entityView;

    public WorldSession() {
        this.chunkView = new ChunkViewRenderer();
        this.entityView = new EntityViewRenderer();
    }

    public void setWorld(ClientWorld world) {
        Openverse.logger().info("Setting world to: " + world.getName());
        this.world = world;
        chunkView.setWorld(world);
    }

    protected void onSetPosition(double x, double y, double z) {
        chunkView.setPosition(
                Math.floorMod((int) Math.floor(x), 16),
                Math.floorMod((int) Math.floor(y), 16),
                Math.floorMod((int) Math.floor(z), 16));
        // todo entityView.setPosition(x, y, z);
    }
}
