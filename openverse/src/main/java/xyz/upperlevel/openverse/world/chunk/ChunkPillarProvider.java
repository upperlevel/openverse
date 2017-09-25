package xyz.upperlevel.openverse.world.chunk;

public interface ChunkPillarProvider {
    ChunkPillar getChunkPillar(int x, int z);

    void setChunkPillar(int x, int z, ChunkPillar chunkPillar);

    boolean unloadChunkPillar(int x, int z);
}
