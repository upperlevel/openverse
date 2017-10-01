package xyz.upperlevel.openverse.server.world.generators;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.world.ChunkGenerator;
import xyz.upperlevel.openverse.server.world.generators.util.SimplexNoise;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

public class SimpleWorldGenerator implements ChunkGenerator {
    private static final int MAX_HEIGHT = 50;
    private static final double FREQUENCY = 100;

    private final BlockType dirtType, grassType;

    public SimpleWorldGenerator() {
        dirtType = Openverse.resources().blockTypes().entry("dirt");
        grassType = Openverse.resources().blockTypes().entry("grass");
    }

    @Override
    public void buildHeightMap(ChunkPillar chunkPillar) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int bx = x + chunkPillar.getX() * 16;
                int bz = z + chunkPillar.getZ() * 16;
                chunkPillar.setHeight(x, z, (int) ((SimplexNoise.noise(bx / FREQUENCY, bz / FREQUENCY) + 1 / 2.0) * MAX_HEIGHT));
            }
        }
        chunkPillar.setHeightMapGenerated(true);
    }

    @Override
    public void generate(Chunk chunk) {
        ChunkPillar chkPil = chunk.getChunkPillar();
        buildHeightMap(chkPil);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int h = chkPil.getHeight(x, z);
                for (int y = 0, by = y + chunk.getY() * 16; y < 16 && by <= h; y++, by++) {
                    if (by == h)
                        chunk.setBlockType(x, y, z, grassType);
                    else
                        chunk.setBlockType(x, y, z, dirtType);
                }
            }
        }
    }
}