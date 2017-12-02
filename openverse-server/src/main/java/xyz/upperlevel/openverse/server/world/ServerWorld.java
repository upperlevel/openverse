package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.entity.EntityWatcher;
import xyz.upperlevel.openverse.server.world.generators.SimpleWorldGenerator;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import static xyz.upperlevel.openverse.Openverse.logger;

@Getter
public class ServerWorld extends World {
    private final PlayerChunkMap chunkMap;
    private final EntityWatcher entityWatcher;
    private final ChunkGenerator generator;

    public ServerWorld(String name) {
        super(name);
        this.generator = new SimpleWorldGenerator();
        this.chunkMap = new PlayerChunkMap(this, 4);
        this.entityWatcher = new EntityWatcher(chunkMap);

        // TODO TEMPORARY CHUNKS GENERATION!
        logger().info("Generating chunks in a radius of " + 5);
        for (int cx = -5; cx <= 5; cx++) {
            for (int cy = -5; cy <= 5; cy++) {
                for (int cz = -5; cz <= 5; cz++) {
                    generator.generate(getChunk(cx, cy, cz));
                }
            }
        }
        logger().info("Chunks generated!");
    }

    public void onTick() {
        entityWatcher.onTick();
    }
}
