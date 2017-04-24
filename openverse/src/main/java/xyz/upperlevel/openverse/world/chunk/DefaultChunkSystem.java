package xyz.upperlevel.openverse.world.chunk;

import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

public class DefaultChunkSystem extends BaseChunkSystem {

    private Map<ChunkLocation, Chunk> chunks = new HashMap<>();

    public DefaultChunkSystem(World world) {
        super(world);
    }


    public void set0(Chunk chunk) {
        chunks.put(ChunkLocation.loc(chunk), chunk);
    }

    public void remove(Chunk chunk) {
        chunks.remove(ChunkLocation.loc(chunk));
    }

    @Override
    public Chunk get0(int x, int y, int z) {
        return chunks.get(new ChunkLocation(x, y, z));
    }

    @Override
    public Chunk get(ChunkLocation loc) {
        return chunks.get(loc);
    }
}
