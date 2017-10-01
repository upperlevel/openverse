package xyz.upperlevel.openverse.world.chunk;

import xyz.upperlevel.openverse.world.World;

public interface VerticalChunkProvider {
    World getWorld();

    ChunkPillar getChunkPillar();

    Chunk getChunk(int y);

    void setChunk(int y, Chunk chunk);

    boolean hasChunk(int y);

    boolean unloadChunk(int y);
}
