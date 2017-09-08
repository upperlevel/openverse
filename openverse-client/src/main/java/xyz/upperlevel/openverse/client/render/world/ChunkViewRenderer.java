package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.event.ChunkLoadEvent;
import xyz.upperlevel.openverse.world.event.ChunkUnloadEvent;
import xyz.upperlevel.ulge.opengl.shader.Program;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains a list of all chunks that have to be rendered.
 */
@Getter
public class ChunkViewRenderer implements Listener {
    public static final int MAX_RENDER_DISTANCE = 3;

    private final Program program;
    private ClientWorld world;

    private int distance;
    private Map<ChunkLocation, ChunkRenderer> chunks = new HashMap<>();

    public ChunkViewRenderer(Program program) {
        this.program = program;
        this.distance = 1;
        Openverse.getEventManager().register(this);
    }

    public void loadChunk(ChunkRenderer chunk) {
        chunks.put(chunk.getLocation(), chunk);
    }

    public void unloadChunk(ChunkLocation location) {
        ChunkRenderer chunk = chunks.remove(location);
        if(chunk != null)
            chunk.destroy();
    }

    public ChunkRenderer getChunk(ChunkLocation location) {
        return chunks.get(location);
    }

    public Collection<ChunkRenderer> getChunks() {
        return chunks.values();
    }

    public void setWorld(ClientWorld world) {
        this.world = world;
        //destroy();
    }

    /**
     * Destroys all chunks and remove them from memory.
     */
    public void destroy() {
        chunks.values().forEach(ChunkRenderer::destroy);
        chunks.clear();
    }


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        loadChunk(new ChunkRenderer(e.getChunk(), program));
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        unloadChunk(e.getLocation());
    }
}
