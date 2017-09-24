package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.openverse.world.chunk.Chunk;

public interface ChunkGenerator {

    void generate(Chunk chunk);
}
