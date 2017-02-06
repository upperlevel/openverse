package xyz.upperlevel.opencraft.world;

public class FlatChunkGenerator implements ChunkGenerator {

    @Override
    public void generate(Chunk chunk, int x, int y, int z) {
        for (int mx = 0; mx < chunk.getWidth(); mx++) {
            for (int my = 0; my < 3; my++) {
                for (int mz = 0; mz < chunk.getLength(); mz++) {
                    chunk.getBlock(mx, my, mz).setType(BlockTypes.TEST_BLOCK);
                }
            }
        }
    }
}
