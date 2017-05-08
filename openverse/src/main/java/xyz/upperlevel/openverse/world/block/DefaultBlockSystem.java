package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import static xyz.upperlevel.openverse.world.chunk.Chunk.HEIGHT;
import static xyz.upperlevel.openverse.world.chunk.Chunk.LENGTH;
import static xyz.upperlevel.openverse.world.chunk.Chunk.WIDTH;

public class DefaultBlockSystem extends BlockSystem {

    @Getter
    private final Block[][][] blocks = new Block[WIDTH][HEIGHT][LENGTH];

    public DefaultBlockSystem(Chunk chunk) {
        super(chunk);
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                for (int z = 0; z < LENGTH; z++)
                    blocks[x][y][z] = new Block(getChunk(), x, y, z);
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }
}
