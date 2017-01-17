package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.Blocks;
import xyz.upperlevel.opencraft.world.BlockData;
import xyz.upperlevel.opencraft.world.Chunk;
import xyz.upperlevel.opencraft.world.ChunkCache;
import xyz.upperlevel.opencraft.world.ChunkGenerator;

public class FlatChunkGenerator implements ChunkGenerator {

    @Override
    public void generate(ChunkCache data, int x, int y, int z) {
        System.out.println("Generating flat chunk");
        for (int mx = 0; mx < Chunk.WIDTH; mx++) {
            for (int my = 0; my < 3; my++) {
                for (int mz = 0; mz < Chunk.LENGTH; mz++) {
                    data.getBlock(mx, my, mz).setData(Blocks.WOOD, false);
                }
            }
        }
        for (int mx = 0; mx < Chunk.WIDTH; mx++)
            for (int my = 0; my < 3; my++)
                for (int mz = 0; mz < Chunk.LENGTH; mz++)
                    data.getBlock(mx, my, mz).update();
    }
}
