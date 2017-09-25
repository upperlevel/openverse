package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

public interface ChunkGenerator {
    void buildHeightMap(ChunkPillar chunkPillar);

    void generate(Chunk chunk);
}
