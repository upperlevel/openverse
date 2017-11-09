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
@Getter
public class WorldSession implements PacketListener {
    private final Program program;
    private ClientWorld world;
    private final ChunkViewRenderer chunkView;
    private final EntityViewRenderer entityView;

    public WorldSession(Program program) {
        this.program = program;
        this.chunkView = new ChunkViewRenderer(program);
        this.entityView = new EntityViewRenderer();
        this.world = (ClientWorld) OpenverseClient.get().getPlayer().getWorld();
        chunkView.setWorld(world);
    }

    public void setWorld(ClientWorld world) {
        Openverse.logger().info("Setting world to: " + world.getName());
        this.world = world;
        chunkView.setWorld(world);
    }
}
