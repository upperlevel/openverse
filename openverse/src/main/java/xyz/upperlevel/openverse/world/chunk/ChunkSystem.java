package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.World;

// todo review chunk system chunk locs
@Getter
@RequiredArgsConstructor
public abstract class ChunkSystem {
    private final World world;

    public abstract Chunk getChunk(int x, int y, int z);

    public Chunk getChunk(ChunkLocation loc) {
        return getChunk(loc.x, loc.y, loc.z);
    }

    public abstract void setChunk(int x, int y, int z, Chunk chunk);

    public void setChunk(ChunkLocation loc, Chunk chunk) {
        setChunk(loc.x, loc.y, loc.z, chunk);
    }

    public abstract void destroyChunk(int x, int y, int z);

    public void destroyChunk(ChunkLocation loc) {
        destroyChunk(loc.x, loc.y, loc.z);
    }
}
