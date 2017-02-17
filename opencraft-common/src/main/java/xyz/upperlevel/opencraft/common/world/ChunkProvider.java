package xyz.upperlevel.opencraft.common.world;

public interface ChunkProvider {

    ChunkProvider NULL = Chunk::new;

    Chunk provideChunk(World world, int x, int y, int z);
}