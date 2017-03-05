package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;

import java.util.*;

public class World {

    @Getter
    private ChunkGenerator generator;

    @Getter
    private List<Chunk> loadedChunks = new ArrayList<>();

    @Getter
    private Location spawn = new Location(this);

    public World() {
        this(null);
    }

    public World(ChunkGenerator generator) {
        setGenerator(generator);
    }

    public void setGenerator(ChunkGenerator generator) {
        this.generator = generator != null ? generator : ChunkGenerator.NULL;
    }

    public boolean isLoaded(Chunk chunk) {
        return loadedChunks.contains(chunk);
    }

    public boolean isLoaded(int x, int y, int z) {
        for (Chunk chunk : loadedChunks)
            if (chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z)
                return true;
        return false;
    }

    public World loadChunk(Chunk chunk) {
        if (!isLoaded(chunk))
            loadedChunks.add(chunk);
        return this;
    }

    public World loadChunk(int x, int y, int z) {
        if (!isLoaded(x, y, z))
            loadedChunks.add(new Chunk(this, x, y, z));
        return this;
    }

    public boolean unloadChunk(Chunk chunk) {
        return loadedChunks.remove(chunk);
    }

    public boolean unloadChunk(int x, int y, int z) {
        for (Iterator<Chunk> i = loadedChunks.iterator(); i.hasNext(); ) {
            Chunk chunk = i.next();
            if (chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z) {
                i.remove();
                return true;
            }
        }
        return false;
    }

    public Chunk getChunk(int x, int y, int z) {
        for (Chunk chunk : loadedChunks)
            if (chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z)
                return chunk;
        return new Chunk(this, x, y, z).generate();
    }

    public Block getBlock(int x, int y, int z) {
        int cx = x / Chunk.WIDTH;
        int cy = y / Chunk.HEIGHT;
        int cz = z / Chunk.LENGTH;

        int cbx = x % Chunk.WIDTH;
        int cby = y % Chunk.HEIGHT;
        int cbz = z % Chunk.LENGTH;

        return getChunk(cx, cy, cz).getBlock(cbx, cby, cbz);
    }
}