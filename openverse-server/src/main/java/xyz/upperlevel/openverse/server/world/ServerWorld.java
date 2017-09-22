package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.world.generators.SingleBlockGenerator;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.server.world.generators.ChunkGenerator;
import xyz.upperlevel.openverse.server.world.generators.FlatChunkGenerator;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

@Getter
public class ServerWorld extends World {
    private final PlayerChunkMap chunkMap;
    private final ChunkGenerator generator;

    public ServerWorld(String name) {
        super(name);
        this.generator = new FlatChunkGenerator(1, Openverse.resources().blockTypes().entry("grass"), BlockType.AIR);
        this.chunkMap = new PlayerChunkMap(this, 4);
    }

    @Override
    public Chunk getChunk(ChunkLocation loc) {
        Chunk chk = super.getChunk(loc);
        generator.generate(chk);
        return chk;
    }
}
