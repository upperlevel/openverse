package xyz.upperlevel.opencraft.server.world;

public interface ChunkProvider {

    ChunkProvider NULL = Chunk::new;

    Chunk provideChunk(World world, int x, int y, int z);
}