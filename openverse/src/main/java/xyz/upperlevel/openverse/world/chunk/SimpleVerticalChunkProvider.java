package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.event.ChunkLoadEvent;
import xyz.upperlevel.openverse.world.event.ChunkUnloadEvent;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SimpleVerticalChunkProvider implements VerticalChunkProvider {
    private final World world;
    private final ChunkPillar chunkPillar;
    private Map<Integer, Chunk> chunksMap = new HashMap<>();

    public SimpleVerticalChunkProvider(ChunkPillar chunkPillar) {
        this.world = chunkPillar.getWorld();
        this.chunkPillar = chunkPillar;
    }

    public Chunk getChunk(int y) {
        return chunksMap.get(y);
    }

    @Override
    public void setChunk(int y, Chunk chunk) {
        chunksMap.put(y, chunk);
        Openverse.getEventManager().call(new ChunkLoadEvent(chunk));
    }

    @Override
    public boolean hasChunk(int y) {
        return chunksMap.containsKey(y);
    }

    @Override
    public Chunk unloadChunk(int y) {
        Chunk removed = chunksMap.remove(y);
        if (removed != null) {
            Openverse.getEventManager().call(new ChunkUnloadEvent(removed));
        }
        return removed;
    }
}
