package xyz.upperlevel.openverse.server.world.generators;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
public class SingleBlockGenerator implements ChunkGenerator {
    private final BlockType fullType, emptyType;

    public SingleBlockGenerator(BlockType fullType, BlockType emptyType) {
        this.fullType = fullType;
        this.emptyType = emptyType;
    }

    @Override
    public void generate(Chunk chunk) {
        for (int x = 0; x < Chunk.WIDTH; x++) {
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int z = 0; z < Chunk.LENGTH; z++) {
                    chunk.setBlockType(x, y, z, emptyType);
                }
            }
        }
        chunk.setBlockType(0, 0, 0, fullType);
    }
}