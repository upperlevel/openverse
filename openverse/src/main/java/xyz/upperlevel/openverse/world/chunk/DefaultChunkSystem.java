package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

public class DefaultChunkSystem extends ChunkSystem {

    private final Map<ChunkLocation, Chunk> chunkCache = new HashMap<>();

    @Getter
    @Setter
    private ChunkGenerator generator = null;

    public DefaultChunkSystem(World world) {
        super(world);
    }

    public boolean isLoaded(ChunkLocation location) {
        return chunkCache.containsKey(location);
    }

    public boolean isLoaded(Chunk chunk) {
        return isLoaded(chunk.getLocation());
    }

    private void addChunk(Chunk chunk) {
        chunkCache.put(chunk.getLocation(), chunk);
        if (generator != null)
            generator.generate(chunk);
    }

    public Chunk addChunk(ChunkLocation location) {
        Chunk chunk = new Chunk(getWorld(), location);
        addChunk(chunk);
        return chunk;
    }

    public boolean removeChunk(ChunkLocation location) {
        return chunkCache.remove(location) != null;
    }

    @Override
    public Chunk getChunk(int x, int y, int z) {
        return getChunk(new ChunkLocation(x, y, z));
    }

    @Override
    public Chunk getChunk(ChunkLocation location) {
        if (isLoaded(location))
            return chunkCache.get(location);
        return addChunk(location);
    }
}
