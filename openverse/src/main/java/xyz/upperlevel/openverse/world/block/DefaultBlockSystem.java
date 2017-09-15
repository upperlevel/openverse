package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import static xyz.upperlevel.openverse.world.chunk.Chunk.HEIGHT;
import static xyz.upperlevel.openverse.world.chunk.Chunk.LENGTH;
import static xyz.upperlevel.openverse.world.chunk.Chunk.WIDTH;

public class DefaultBlockSystem extends BlockSystem {

    @Getter
    private final BlockType[][][] blocks = new BlockType[WIDTH][HEIGHT][LENGTH];

    public DefaultBlockSystem(Chunk chunk) {
        super(chunk);
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return new Block(getChunk(), x, y, z, this);
    }

    @Override
    public BlockType getBlockType(int x, int y, int z) {
        return blocks[x][y][z];
    }

    @Override
    public void setBlockType(int x, int y, int z, BlockType type) {
        blocks[x][y][z] = type;
    }
}
