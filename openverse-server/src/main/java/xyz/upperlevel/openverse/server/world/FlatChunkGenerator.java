package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@RequiredArgsConstructor
public class FlatChunkGenerator implements ChunkGenerator {
    private final int maxHeight;
    private final BlockType fullType, emptyType;

    @Override
    public void generate(Chunk chunk) {
        int maxY = maxHeight - chunk.getY() << 4;
        for (int x = 0; x < Chunk.WIDTH; x++)
            for (int z = 0; z < Chunk.LENGTH; z++)
                for (int y = 0; y < Chunk.HEIGHT; y++)
                    chunk.getBlock(x, y, z).setType(y <= maxY ? fullType : emptyType);
    }
}
