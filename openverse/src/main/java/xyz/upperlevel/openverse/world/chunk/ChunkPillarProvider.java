package xyz.upperlevel.openverse.world.chunk;

public interface ChunkPillarProvider {
    ChunkPillar getChunkPillar(int x, int z, boolean load);

    default ChunkPillar getChunkPillar(int x, int z) {
        return getChunkPillar(x, z, true);//Safer
    }

    void setChunkPillar(ChunkPillar chunkPillar);

    boolean hasPillar(int x, int z);

    default boolean hasChunk(int x, int y, int z) {
        ChunkPillar pillar = getChunkPillar(x, z, false);
        return pillar != null && pillar.hasChunk(y);
    }

    boolean unloadChunkPillar(int x, int z);
}
