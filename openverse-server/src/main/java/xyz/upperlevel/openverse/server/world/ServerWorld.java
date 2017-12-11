package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.chunk.PlayerChunkMap;
import xyz.upperlevel.openverse.server.world.entity.EntityWatcher;
import xyz.upperlevel.openverse.server.world.generators.SimpleWorldGenerator;
import xyz.upperlevel.openverse.world.World;

@Getter
public class ServerWorld extends World {
    private final PlayerChunkMap chunkMap;
    private final EntityWatcher entityWatcher;
    private final ChunkGenerator chunkGenerator;

    public ServerWorld(String name) {
        super(name);
        this.chunkGenerator = new SimpleWorldGenerator(100, 50);
        this.chunkMap = new PlayerChunkMap(this, 4);
        this.entityWatcher = new EntityWatcher(chunkMap);
    }

    public void onTick() {
        entityWatcher.onTick();
    }
}
