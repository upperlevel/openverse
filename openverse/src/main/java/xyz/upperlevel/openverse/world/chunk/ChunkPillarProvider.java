package xyz.upperlevel.openverse.world.chunk;

public interface ChunkPillarProvider {
    ChunkPillar getChunkPillar(int x, int z);

    void setChunkPillar(ChunkPillar chunkPillar);

    boolean hasPillar(int x, int z);

    default boolean hasChunk(int x, int y, int z) {
        ChunkPillar pillar = getChunkPillar(x, z);
        return pillar != null && pillar.hasChunk(y);
    }

    ChunkPillar unloadChunkPillar(int x, int z);
}
