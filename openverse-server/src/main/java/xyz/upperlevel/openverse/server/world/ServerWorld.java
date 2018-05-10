package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.chunk.PlayerChunkMapListener;
import xyz.upperlevel.openverse.server.world.entity.EntityWatcher;
import xyz.upperlevel.openverse.server.world.generators.SimpleWorldGenerator;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

@Getter
public class ServerWorld extends World {
    private final PlayerChunkMapListener chunkMap;
    private final EntityWatcher entityWatcher;
    private final ChunkGenerator chunkGenerator;

    public ServerWorld(String name) {
        super(name);
        this.chunkGenerator = new SimpleWorldGenerator(100, 50);
        this.chunkMap = new PlayerChunkMapListener(this, 4);
        this.entityWatcher = new EntityWatcher(chunkMap);
    }

    public ChunkPillar getOrLoadChunkPillar(int x, int z) {
        ChunkPillar plr = getChunkPillar(x, z);
        if (plr == null) {
            plr = loadChunkPillar(x, z);
        }
        return plr;
    }

    public ChunkPillar loadChunkPillar(int x, int z) {
        ChunkPillar plr = new ChunkPillar(this, x, z);
        chunkGenerator.generateHeightmap(plr);
        setChunkPillar(plr);
        return plr;
    }

    public Chunk getOrLoadChunk(int x, int y, int z) {
        Chunk chk = getChunk(x, y, z);
        if (chk == null) {
            chk = loadChunk(x, y, z);
        }
        return chk;
    }

    public Chunk loadChunk(int x, int y, int z) {
        Chunk chk = new Chunk(getOrLoadChunkPillar(x, z), y);
        chunkGenerator.generateChunk(chk);
        setChunk(chk);
        chk.updateNearbyChunksLights();
        return chk;
    }

    public void onTick() {
        entityWatcher.onTick();
    }
}
