package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.Blocks;

public class FlatChunkGenerator implements ChunkGenerator {

    @Override
    public void generate(ChunkCache data, int x, int y, int z) {
        System.out.println("Generating flat chunk");
    }
}
