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
        chunk.getBlock(0,0,0).setType(fullType);
    }
}
