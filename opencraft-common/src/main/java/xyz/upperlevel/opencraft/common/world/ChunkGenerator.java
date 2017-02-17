package xyz.upperlevel.opencraft.common.world;

public interface ChunkGenerator {

    ChunkGenerator NULL = chunk -> {};

    void generate(Chunk chunk);
}
