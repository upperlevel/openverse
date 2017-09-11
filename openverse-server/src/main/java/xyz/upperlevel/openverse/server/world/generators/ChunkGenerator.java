package xyz.upperlevel.openverse.server.world.generators;

import xyz.upperlevel.openverse.world.chunk.Chunk;

public interface ChunkGenerator {

    void generate(Chunk chunk);
}
