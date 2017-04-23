package xyz.upperlevel.openverse.world;

import lombok.Getter;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;

public class World {

    @Getter
    private final String name;

    @Getter
    private final ChunkSystem chunks;

    public World(String name, ChunkSystem chunks) {
        this.name = name;
        this.chunks = chunks;
    }

    public Chunk getChunk(int x, int y, int z) {
        return chunks.get(x, y, z);
    }

    public Block getBlock(int x, int y, int z) {
        return   getChunk(x << 4 , y << 4 , z << 4 )
                .getBlock(x & 0xF, y & 0xF, z & 0xF);
    }
}