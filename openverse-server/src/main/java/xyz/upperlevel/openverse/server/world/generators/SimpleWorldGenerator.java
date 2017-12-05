package xyz.upperlevel.openverse.server.world.generators;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.world.ChunkGenerator;
import xyz.upperlevel.openverse.server.world.generators.util.SimplexNoise;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;
import xyz.upperlevel.openverse.world.chunk.HeightmapPacket;

public class SimpleWorldGenerator implements ChunkGenerator {
    private final float frequency;
    private final int maxHeight;
    private final BlockType dirtType, grassType, photonType;

    public SimpleWorldGenerator(float frequency, int maxHeight) {
        this.frequency = frequency;
        this.maxHeight = maxHeight;
        dirtType = Openverse.resources().blockTypes().entry("dirt");
        grassType = Openverse.resources().blockTypes().entry("grass");
        photonType = Openverse.resources().blockTypes().entry("photon");
    }

    public int getNoisedHeight(int x, int z) {
        return (int) ((SimplexNoise.noise(x / frequency, z / frequency) + 1 / 2.0) * maxHeight);
    }

    @Override
    public void generateHeightmap(ChunkPillar chunkPillar) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int wx = x + chunkPillar.getX() * 16;
                int wz = z + chunkPillar.getZ() * 16;
                chunkPillar.setHeight(x, z, Integer.MIN_VALUE);
                // getNoisedHeight(wx, wz)
            }
        }
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int wx = (chunk.getX() << 4 | x);
                int wz = (chunk.getZ() << 4 | z);
                int nh = getNoisedHeight(wx, wz);

                for (int y = 0; y < 16; y++) {
                    int wy = (chunk.getY() << 4 | y);
                    if (wy < nh) {
                        chunk.setBlockType(x, y, z, dirtType, false);
                    } else if (wy == nh) {
                        chunk.setBlockType(x, y, z, grassType, false);
                    }
                }
            }
        }
        // Appends block skylights for the chunk and updates (diffuses) them
        chunk.appendBlockSkylights(true);
        Openverse.getLogger().info("Skylight diffused for chunk " + chunk.getX() + " " + chunk.getY());
    }
}