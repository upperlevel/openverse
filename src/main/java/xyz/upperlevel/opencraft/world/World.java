package xyz.upperlevel.opencraft.world;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class World {

    @Getter @Setter @NonNull private ChunkGenerator chunkGenerator;
    @Getter private final List<Chunk> loadedChunks = new ArrayList<>();

    public World() {
        this(ChunkGenerator.ANY);
    }

    public Chunk getChunk(int x, int y, int z) {
        Optional<Chunk> result = loadedChunks.stream()
                .filter(chunk -> chunk.x == x && chunk.z == z)
                .findAny();
        return result.isPresent() ? result.get() : new Chunk(this, x, y, z);
    }

    public int getChunkX(double x) {
        return (int) (x / Chunk.WIDTH);
    }

    public int getChunkY(double y) {
        return (int) (y / Chunk.HEIGHT);
    }

    public int getChunkZ(double z) {
        return (int) (z / Chunk.LENGTH);
    }

    public double asChunkX(double x) {
        return x % Chunk.WIDTH;
    }

    public double asChunkY(double y) {
        return y % Chunk.HEIGHT;
    }

    public double asChunkZ(double z) {
        return z % Chunk.LENGTH;
    }

    public boolean isLoaded(Chunk chunk) {
        return loadedChunks.contains(chunk);
    }

    void loadChunk(Chunk chunk) {
        loadedChunks.add(chunk);
    }

    void unloadChunk(Chunk chunk) {
        loadedChunks.remove(chunk);
    }

    public Block getBlock(int x, int y, int z) {
        Chunk chunk = getChunk(getChunkX(x), getChunkY(y), getChunkZ(z));
        return chunk.getBlock((int) asChunkX(x), y, (int) asChunkZ(z));
    }
}