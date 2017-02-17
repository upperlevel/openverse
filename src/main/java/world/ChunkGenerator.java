package world;

public interface ChunkGenerator {

    ChunkGenerator NULL = (data, x, y, z) -> {};

    void generate(Chunk chunk, int x, int y, int z);
}