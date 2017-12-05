package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

public interface ChunkGenerator {
    /**
     * Generates the heightmap for the given {@link ChunkPillar}.
     *
     * @param chunkPillar the {@link ChunkPillar}
     */
    void generateHeightmap(ChunkPillar chunkPillar);

    /**
     * Generates the blocks of the given {@link Chunk}.
     *
     * @param chunk the {@link Chunk}
     */
    void generateChunk(Chunk chunk);
}
