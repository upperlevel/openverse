package xyz.upperlevel.openverse.server.world.generators;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.ChunkGenerator;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;

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
    public void generate(Chunk chunk) {
        int limitY = heightChunk == chunk.getY() ? maxHeight & 4 : heightChunk > chunk.getY() ? Chunk.HEIGHT : 0;
        for (int x = 0; x < Chunk.WIDTH; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int z = 0; z < Chunk.LENGTH; z++) {
                    chunk.setBlockType(x, y, z, y < limitY ? fullType : emptyType);
                }
            }
        }
    }
}
