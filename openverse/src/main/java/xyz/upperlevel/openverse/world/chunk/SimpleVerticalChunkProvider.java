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
        if (chunksMap.containsKey(y)) {
            return chunksMap.get(y);
        } else {
            Chunk res = new Chunk(chunkPillar, y);
            chunksMap.put(y, res);
            return res;
        }
    }

    @Override
    public void setChunk(int y, Chunk chunk) {
        chunksMap.put(y, chunk);
        Openverse.getEventManager().call(new ChunkLoadEvent(chunk));
    }

    @Override
    public boolean unloadChunk(int y) {
        Chunk removed = chunksMap.remove(y);
        if (removed != null) {
            Openverse.getEventManager().call(new ChunkUnloadEvent(removed));
            return true;
        }
        return false;
    }
}
