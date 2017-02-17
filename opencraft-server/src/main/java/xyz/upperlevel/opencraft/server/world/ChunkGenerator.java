package xyz.upperlevel.opencraft.server.world;

public interface ChunkGenerator {

    ChunkGenerator NULL = chunk -> {};

    void generate(Chunk chunk);
}
