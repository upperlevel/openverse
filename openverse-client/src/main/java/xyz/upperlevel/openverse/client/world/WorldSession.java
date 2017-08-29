package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import xyz.upperlevel.hermes.reflect.PacketListener;
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
        this.world = world;
        chunkView.setWorld(world);
    }

    protected void onSetPosition(int x, int y, int z) {
        // todo chunkView.setPosition(x, y, z);
        // todo entityView.setPosition(x, y, z);
    }
}
