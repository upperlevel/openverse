package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkSystem;
import xyz.upperlevel.openverse.world.chunk.DefaultChunkSystem;

import static java.lang.Math.floor;

@Getter
public class World {
    private final String name;
    private ChunkSystem chunkSystem;

    public World(String name) {
        this.name = name;
        chunkSystem = new DefaultChunkSystem(this);
    }

    public Chunk getChunk(int x, int y, int z) {
        return chunkSystem.getChunk(x, y, z);
    }

    public Chunk getChunk(ChunkLocation loc) {
        // todo review chunk system chunk locs
        return chunkSystem.getChunk(loc.x, loc.y, loc.z);
    }

    public Chunk getChunk(double x, double y, double z) {
        return getChunk((int) floor(x), (int) floor(y), (int) floor(z));
    }

    public void setChunk(int x, int y, int z, Chunk chunk) {
        chunkSystem.setChunk(x, y, z, chunk);
    }

    public void setChunk(ChunkLocation loc, Chunk chunk) {
        chunkSystem.setChunk(loc.x, loc.y, loc.z, chunk);
    }

    public void unloadChunk(int x, int y, int z) {
        chunkSystem.destroyChunk(x, y, z);
    }

    public void unloadChunk(ChunkLocation loc) {
        chunkSystem.destroyChunk(loc.x, loc.y, loc.z);
    }

    public Block getBlock(int x, int y, int z) {
        return   getChunk(x << 4 , y << 4 , z << 4 )
                .getBlock(x & 0xF, y & 0xF, z & 0xF);
    }

    public Block getBlock(double x, double y, double z) {
        return getBlock((int) floor(x), (int) floor(y), (int) floor(z));
    }

    public Block getBlock(Location location) {
        return getBlock(location.getX(), location.getY(), location.getZ());
    }
}