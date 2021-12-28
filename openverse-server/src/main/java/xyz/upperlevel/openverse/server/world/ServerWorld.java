package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.world.chunk.PlayerChunkMapListener;
import xyz.upperlevel.openverse.server.world.entity.EntityWatcher;
import xyz.upperlevel.openverse.server.world.generators.SimpleWorldGenerator;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;

public class ServerWorld extends World {
    @Getter
    private final PlayerChunkMapListener chunkMap;

    @Getter
    private final EntityWatcher entityWatcher;

    @Getter
    private final ChunkGenerator chunkGenerator;

    public ServerWorld(OpenverseServer server, String name) {
        super(server, name);

        this.chunkGenerator = new SimpleWorldGenerator(server, 100, 50);
        this.chunkMap = new PlayerChunkMapListener(server, this, 4);
        this.entityWatcher = new EntityWatcher(server, chunkMap);
    }

    public ChunkPillar getOrLoadChunkPillar(int x, int z) {
        ChunkPillar plr = getChunkPillar(x, z);
        if (plr == null) {
            plr = loadChunkPillar(x, z);
        }
        return plr;
    }

    public ChunkPillar loadChunkPillar(int x, int z) {
        ChunkPillar plr = new ChunkPillar(getModule(), this, x, z);
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
        Chunk chk = new Chunk(getModule(), getOrLoadChunkPillar(x, z), y);
        chunkGenerator.generateChunk(chk);
        setChunk(chk);
        chk.updateNearbyChunksLights();
        return chk;
    }

    public void onTick() {
        entityWatcher.onTick();
    }
}
