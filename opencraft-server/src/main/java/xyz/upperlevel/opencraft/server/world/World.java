package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

public class World {

    @Getter
    @Setter
    @NonNull
    private String name;

    @Getter
    private ChunkProvider chunkProvider;

    @Getter
    private ChunkGenerator chunkGenerator;

    @Getter
    private List<Chunk> loadedChunks = new ArrayList<>();

    @Getter
    private Player player = new Player();

    public World(String name) {
        this(name, null, null);
    }

    public World(String name, ChunkProvider chunkProvider, ChunkGenerator chunkGenerator) {
        Objects.requireNonNull(name, "name");
        this.name = name;
        setChunkProvider(chunkProvider);
        setChunkGenerator(chunkGenerator);
    }

    public void setChunkProvider(ChunkProvider chunkProvider) {
        this.chunkProvider = chunkProvider != null ? chunkProvider : ChunkProvider.NULL;
    }

    public void setChunkGenerator(ChunkGenerator chunkGenerator) {
        this.chunkGenerator = chunkGenerator != null ? chunkGenerator : ChunkGenerator.NULL;
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
            loadedChunks.add(chunkProvider.provideChunk(this, x, y, z));
        return this;
    }

    public boolean unloadChunk(Chunk chunk) {
        return loadedChunks.remove(chunk);
    }

    public boolean unloadChunk(int x, int y, int z) {
        for (Iterator<Chunk> chunkIt = loadedChunks.iterator(); chunkIt.hasNext(); ) {
            Chunk chunk = chunkIt.next();
            if (chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z) {
                chunkIt.remove();
                return true;
            }
        }
        return false;
    }

    public Chunk getChunk(int x, int y, int z) {
        for (Chunk chunk : loadedChunks)
            if (chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z)
                return chunk;
        return chunkProvider.provideChunk(this, x, y, z);
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