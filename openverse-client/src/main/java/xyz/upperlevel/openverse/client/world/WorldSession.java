package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.entity.EntityViewRenderer;
import xyz.upperlevel.openverse.client.render.world.ChunkViewRenderer;
import xyz.upperlevel.ulge.opengl.shader.Program;

/**
 * Handles world renderers and changes.
 */
public class WorldSession implements PacketListener {
    @Getter
    private final OpenverseClient client;

    @Getter
    private final Program program;

    @Getter
    private ClientWorld world;

    @Getter
    private final ChunkViewRenderer chunkView;

    @Getter
    private final EntityViewRenderer entityView;

    public WorldSession(OpenverseClient client, Program program) {
        this.client = client;

        this.program = program;
        this.chunkView = new ChunkViewRenderer(client, program);
        this.entityView = new EntityViewRenderer(client);
        this.world = (ClientWorld) OpenverseClient.get().getPlayer().getWorld();
        chunkView.setWorld(world);
    }

    public void setWorld(ClientWorld world) {
        client.getLogger().info("Setting world to: " + world.getName());
        this.world = world;
        chunkView.setWorld(world);
    }
}
