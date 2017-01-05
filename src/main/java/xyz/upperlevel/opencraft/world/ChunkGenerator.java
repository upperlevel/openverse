package xyz.upperlevel.opencraft.world;

public interface ChunkGenerator {

    ChunkGenerator ANY = (data, x, y, z) -> {};

    void generate(ChunkCache data, int x, int y, int z);
}