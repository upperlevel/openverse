package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

public class DefaultChunkSystem extends ChunkSystem {

    private final Map<ChunkLocation, Chunk> chunks = new HashMap<>();

    @Getter
    @Setter
    private ChunkGenerator generator = null;

    public DefaultChunkSystem(World world) {
        super(world);
    }

    public boolean isLoaded(ChunkLocation location) {
        return chunks.containsKey(location);
    }

    public boolean isLoaded(Chunk chunk) {
        return isLoaded(chunk.getLocation());
    }

    private void addChunk(Chunk chunk) {
        chunks.put(chunk.getLocation(), chunk);
        if (generator != null)
            generator.generate(chunk);
    }

    public Chunk addChunk(ChunkLocation location) {
        Chunk chunk = new Chunk(getWorld(), location);
        addChunk(chunk);
        return chunk;
    }

    public boolean removeChunk(ChunkLocation location) {
        return chunks.remove(location) != null;
    }

    @Override
    public Chunk getChunk(int x, int y, int z) {
        ChunkLocation loc = new ChunkLocation(x, y, z);
        if (isLoaded(loc))
            return chunks.get(loc);
        return addChunk(loc);
    }

    @Override
    public void setChunk(int x, int y, int z, Chunk chunk) {
        chunks.put(new ChunkLocation(x, y, z), chunk);
    }

    @Override
    public void destroyChunk(int x, int y, int z) {
        chunks.remove(new ChunkLocation(x, y, z));
    }
}
