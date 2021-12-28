package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.event.ChunkLoadEvent;
import xyz.upperlevel.openverse.world.event.ChunkUnloadEvent;

import java.util.HashMap;
import java.util.Map;

public class SimpleVerticalChunkProvider implements VerticalChunkProvider {
    @Getter
    private final OpenverseProxy module;

    @Getter
    private final World world;

    @Getter
    private final ChunkPillar chunkPillar;

    @Getter
    private final Map<Integer, Chunk> chunksMap = new HashMap<>();

    public SimpleVerticalChunkProvider(OpenverseProxy module, ChunkPillar chunkPillar) {
        this.module = module;

        this.world = chunkPillar.getWorld();
        this.chunkPillar = chunkPillar;
    }

    public Chunk getChunk(int y) {
        return chunksMap.get(y);
    }

    @Override
    public void setChunk(int y, Chunk chunk) {
        chunksMap.put(y, chunk);
        module.getEventManager().call(new ChunkLoadEvent(chunk));
    }

    @Override
    public boolean hasChunk(int y) {
        return chunksMap.containsKey(y);
    }

    @Override
    public Chunk unloadChunk(int y) {
        Chunk removed = chunksMap.remove(y);
        if (removed != null) {
            module.getEventManager().call(new ChunkUnloadEvent(removed));
        }
        return removed;
    }
}
