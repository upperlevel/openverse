package xyz.upperlevel.openverse.world.chunk;

import xyz.upperlevel.openverse.world.World;

import java.util.HashMap;
import java.util.Map;

public class DefaultChunkSystem extends BaseChunkSystem {

    private Map<ChunkLoc, Chunk> chunks = new HashMap<>();

    public DefaultChunkSystem(World world) {
        super(world);
    }


    public void set0(Chunk chunk) {
        chunks.put(ChunkLoc.loc(chunk), chunk);
    }

    public void remove(Chunk chunk) {
        chunks.remove(ChunkLoc.loc(chunk));
    }

    @Override
    public Chunk get0(int x, int y, int z) {
        return chunks.get(new ChunkLoc(x, y, z));
    }

    @Override
    public Chunk get(ChunkLoc loc) {
        return chunks.get(loc);
    }
}
