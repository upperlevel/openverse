package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.world.block.BlockIds;

public class FlatChunkGenerator implements ChunkGenerator {

    @Override
    public void generate(Chunk chunk, int x, int y, int z) {
        for (int mx = 0; mx < chunk.getWidth(); mx++) {
            for (int my = 0; my < Math.min(3, chunk.getHeight()); my++) {
                for (int mz = 0; mz < chunk.getLength(); mz++) {
                    chunk.getBlock(mx, my, mz).setState(BlockIds.TEST_BLOCK.generateState());
                }
            }
        }
    }
}
