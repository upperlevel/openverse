package xyz.upperlevel.openverse.world;

import lombok.Getter;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;

import static java.lang.Math.floor;

public class World {

    @Getter
    private final String name;

    @Getter
    private ChunkSystem chunks;

    public World(String name) {
        this.name = name;
    }

    public void setChunkSystem(ChunkSystem chunks) {
        this.chunks = chunks;
    }

    public Chunk getChunk(int x, int y, int z) {
        return chunks.get(x, y, z);
    }

    public Chunk getChunk(ChunkLocation location) {
        return getChunk(location.x, location.y, location.z);
    }

    public Chunk getChunk(double x, double y, double z) {
        return getChunk((int) floor(x), (int) floor(y), (int) floor(z));
    }

    public Block getBlock(int x, int y, int z) {
        return   getChunk(x << 4 , y << 4 , z << 4 )
                .getBlock(x & 0xF, y & 0xF, z & 0xF);
    }

    public Block getBlock(double x, double y, double z) {
        return getBlock((int) floor(x), (int) floor(y), (int) floor(z));
    }
}