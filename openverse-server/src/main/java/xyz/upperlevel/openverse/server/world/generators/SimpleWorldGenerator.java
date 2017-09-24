package xyz.upperlevel.openverse.server.world.generators;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.world.ChunkGenerator;
import xyz.upperlevel.openverse.server.world.generators.util.SimplexNoise;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;

public class SimpleWorldGenerator implements ChunkGenerator {
    private static final int MAX_HEIGHT = 5;
    private static final double FREQUENCY = 100f;

    private final BlockType fullType;

    public SimpleWorldGenerator() {
        fullType = Openverse.resources().blockTypes().entry("grass");
    }


    @Override
    public void generate(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int bx = x + chunk.getX() * 16;
                int bz = z + chunk.getZ() * 16;
                int h = (int) ((SimplexNoise.noise(bx / FREQUENCY, bz / FREQUENCY) + 1 / 2.0) * MAX_HEIGHT);
                for (int y = 0; y < 16 && (y + chunk.getY() * 16) < h; y++) {
                    chunk.setBlockType(x, y, z, fullType);
                }
            }
        }
    }
}
