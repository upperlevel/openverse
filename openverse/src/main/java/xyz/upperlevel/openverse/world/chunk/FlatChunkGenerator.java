package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.resource.block.BlockType;

@RequiredArgsConstructor
public class FlatChunkGenerator implements ChunkGenerator {

    @Getter
    private final int maxHeight;

    @Getter
    private final BlockType fullType, emptyType;

    @Override
    public void generate(Chunk chunk) {
        int maxY = maxHeight - chunk.getY() << 4;

        for(int x = 0; x < Chunk.WIDTH; x++)
            for(int z = 0; z < Chunk.LENGTH; z++)
                for(int y = 0; y < Chunk.HEIGHT; y++)
                    chunk.getBlock(x, y, z).setType(y <= maxY ? fullType : emptyType);
    }
}
