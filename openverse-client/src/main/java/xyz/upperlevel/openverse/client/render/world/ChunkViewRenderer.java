package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.render.world.util.ArraySlider3d;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.world.chunk.Chunk;

/**
 * This class contains a list of all chunks that have to be rendered.
 */
@Getter
public class ChunkViewRenderer implements Listener {
    public static final int MAX_RENDER_DISTANCE = 3;

    private ClientWorld world;

    private int distance;
    private int x, y, z, side, size;
    private ChunkRenderer[] chunks;

    public ChunkViewRenderer() {
        this.distance = 1;
        this.side = distance * 2 + 1;
        this.size = side * side * side;
        this.chunks = new ChunkRenderer[size];
    }

    private int getIndex1d(int x, int y, int z) {
        return x * side * side + y * side + z;
    }

    private void setChunk(int x, int y, int z, ChunkRenderer chunk) {
        int i = getIndex1d(x, y, z);
        if (i < 0 || i >= size)
            throw new IndexOutOfBoundsException();
        chunks[i] = chunk;
    }

    public ChunkRenderer getChunk(int x, int y, int z) {
        int i = getIndex1d(x, y, z);
        if (i < 0 || i >= size)
            return null;
        return chunks[i];
    }

    /**
     * Sets new position.
     * The position is equivalent to the middle chunk location.
     */
    public void setPosition(int x, int y, int z) {
        for (int ix = 0; ix < side; ix++) {
            for (int iy = 0; iy < side; iy++) {
                for (int iz = 0; iz < side; iz++) {
                    chunks[getIndex1d(ix, iy, iz)] = new ChunkRenderer(world.getChunk(
                            ix - distance + x,
                            iy - distance + y,
                            iz - distance + z
                    ));
                }
            }
        }
    }

    public void setWorld(ClientWorld world) {
        this.world = world;
        for (int i = 0; i < size; i++) {
            if (chunks[i] != null) {
                chunks[i].destroy();
                chunks[i] = null;
            }
        }
        setPosition(x, y, z);
    }

    /**
     * Destroys all chunks and remove them from memory.
     */
    public void destroy() {
        for (int i = 0; i < size; i++) {
            if (chunks[i] != null) {
                chunks[i].destroy();
                chunks[i] = null;
            }
        }
    }
}
