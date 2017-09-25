package xyz.upperlevel.openverse.server.world.generators;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.ChunkGenerator;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

@Getter
public class FlatChunkGenerator implements ChunkGenerator {
    private final int maxHeight;
    private final int heightChunk;
    private final BlockType fullType, emptyType;

    public FlatChunkGenerator(int maxHeight, BlockType fullType, BlockType emptyType) {
        this.maxHeight = maxHeight;
        this.heightChunk = maxHeight >> 16;
        this.fullType = fullType;
        this.emptyType = emptyType;
    }

    @Override
    public void buildHeightMap(ChunkPillar chunkPillar) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunkPillar.setHeight(x, z, maxHeight);
            }
        }
        chunkPillar.setHeightMapGenerated(true);
    }

    @Override
    public void generate(Chunk chunk) {
        int limitY = heightChunk == chunk.getY() ? maxHeight & 4 : (heightChunk > chunk.getY() ? 16 : 0);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlockType(x, y, z, y < limitY ? fullType : emptyType);
                }
            }
        }
    }
}
