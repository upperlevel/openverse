package xyz.upperlevel.openverse.world.chunk;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.resource.BlockType;

@RequiredArgsConstructor
public class FlatWorldGenerator implements ChunkGenerator {

    private final int maxH;
    private final BlockType fullType, emptyType;

    @Override
    public void generate(Chunk chunk) {
        int maxY = maxH - chunk.getY() << 4;

        for(int x = 0; x < Chunk.WIDTH; x++)
            for(int z = 0; z < Chunk.LENGTH; z++)
                for(int y = 0; y < Chunk.HEIGHT; y++)
                    chunk.getData().setType(x, y, z, y <= maxY ? fullType : emptyType);
    }
}
