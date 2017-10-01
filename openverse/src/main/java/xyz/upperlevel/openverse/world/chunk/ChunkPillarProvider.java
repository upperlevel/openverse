package xyz.upperlevel.openverse.world.chunk;

public interface ChunkPillarProvider {
    ChunkPillar getChunkPillar(int x, int z);

    void setChunkPillar(ChunkPillar chunkPillar);

    boolean unloadChunkPillar(int x, int z);
}
