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

    public abstract void setChunk(int x, int y, int z, Chunk chunk);

    public abstract void destroyChunk(int x, int y, int z);
}
