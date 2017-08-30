package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.world.util.ArraySlider3d;
import xyz.upperlevel.openverse.client.world.ClientWorld;

/**
 * This class contains a list of all chunks that have to be rendered.
 */
@Getter
public class ChunkViewRenderer implements Listener {
    public static final int MAX_RENDER_DISTANCE = 3;

    private ClientWorld world;

    // todo merge ArraySlider3d with this class (only used here)
    private final ArraySlider3d<ChunkRenderer> chunks = new ArraySlider3d<ChunkRenderer>(MAX_RENDER_DISTANCE * 2 + 1) {
        @Override
        public ChunkRenderer ask(int x, int y, int z) {
            return world != null ? new ChunkRenderer(world.getChunk(x, y, z)) : null;
        }
    };

    public ChunkViewRenderer() {
        OpenverseClient.get().getEventManager().register(this);
    }

    private void setChunk(int x, int y, int z, ChunkRenderer chunk) {
        chunks.set(x, y, z, chunk);
    }

    public ChunkRenderer getChunk(int x, int y, int z) {
        return chunks.get(x, y, z);
    }

    public ChunkRenderer[] getChunks() {
        return chunks.getData();
    }

    /**
     * Sets new position.
     * The position is equivalent to the middle chunk location.
     */
    public void setPosition(int x, int y, int z) {
        chunks.slideTo(x, y, z);
    }

    /**
     * Sets new world and new position.
     * The position is equivalent to middle chunk's location.
     */
    public void setWorld(ClientWorld world) {
        this.world = world;
        chunks.clear();
        chunks.refresh();
    }

    /**
     * Destroys all chunks and remove them from memory.
     */
    public void destroy() {
        chunks.forEach(ChunkRenderer::destroy);
        chunks.clear();
    }
}
