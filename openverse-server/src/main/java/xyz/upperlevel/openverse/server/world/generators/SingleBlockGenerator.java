package xyz.upperlevel.openverse.server.world.generators;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.ChunkGenerator;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

@Getter
public class SingleBlockGenerator implements ChunkGenerator {
    private final BlockType fullType, emptyType;

    public SingleBlockGenerator(BlockType fullType, BlockType emptyType) {
        this.fullType = fullType;
        this.emptyType = emptyType;
    }

    @Override
    public void buildHeightMap(ChunkPillar chunkPillar) {
        chunkPillar.setHeight(0, 0, Integer.MAX_VALUE); // infinite chunks on y
    }

    @Override
    public void generate(Chunk chunk) {
        chunk.setBlockType(0, 0, 0, fullType, false);
    }
}