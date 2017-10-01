package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.world.generators.FlatChunkGenerator;
import xyz.upperlevel.openverse.server.world.generators.SimpleWorldGenerator;
import xyz.upperlevel.openverse.server.world.generators.SingleBlockGenerator;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
public class ServerWorld extends World {
    private final PlayerChunkMap chunkMap;
    private final ChunkGenerator generator;

    public ServerWorld(String name) {
        super(name);
        this.generator = new SimpleWorldGenerator();
        this.chunkMap = new PlayerChunkMap(this, 4);
    }

    @Override
    public Chunk getChunk(int x, int y, int z) {
        Chunk chk = super.getChunk(x, y, z);
        generator.generate(chk);
        return chk;
    }
}
