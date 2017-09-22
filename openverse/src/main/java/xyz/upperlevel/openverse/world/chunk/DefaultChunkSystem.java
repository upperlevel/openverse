package xyz.upperlevel.openverse.world.chunk;

import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

public class DefaultChunkSystem extends ChunkSystem {
    private final Map<ChunkLocation, Chunk> chunks = new HashMap<>();

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
        return getChunk(new ChunkLocation(x, y, z));
    }

    @Override
    public Chunk getChunk(ChunkLocation loc) {
        if (isLoaded(loc))
            return chunks.get(loc);
        return addChunk(loc);
    }

    @Override
    public void setChunk(int x, int y, int z, Chunk chunk) {
        chunks.put(new ChunkLocation(x, y, z), chunk);
    }

    @Override
    public void setChunk(ChunkLocation loc, Chunk chunk) {
        chunks.put(loc, chunk);
    }

    @Override
    public void destroyChunk(int x, int y, int z) {
        chunks.remove(new ChunkLocation(x, y, z));
    }

    @Override
    public void destroyChunk(ChunkLocation loc) {
        chunks.remove(loc);
    }
}
