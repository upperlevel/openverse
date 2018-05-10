package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.server.world.ServerWorld;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkMap;

@RequiredArgsConstructor
public abstract class PlayerChunkStorage {
    @Getter
    private final ServerWorld world;

    public abstract ChunkMap<PlayerChunkCache> getChunks();

    public PlayerChunkCache get(ChunkLocation location) {
        return getChunks().get(location);
    }

    public PlayerChunkCache get(int x, int y, int z) {
        return getChunks().get(x, y, z);
    }

    @SuppressWarnings("unchecked")
    public PlayerChunkCache getOrCreate(ChunkLocation location) {
        return getChunks().computeIfAbsent(location, this::create);
    }

    protected abstract PlayerChunkCache create(ChunkLocation location);

    public PlayerChunkCache remove(ChunkLocation location) {
        return getChunks().remove(location);
    }

    public PlayerChunkCache remove(int x, int y, int z) {
        return getChunks().remove(x, y, z);
    }

    public void copy(PlayerChunkStorage old) {
        ChunkMap<PlayerChunkCache> chunks = old.getChunks();
        for (PlayerChunkCache cache : chunks) {
            getChunks().put(cache.getLocation(), cache);
        }
    }


}
