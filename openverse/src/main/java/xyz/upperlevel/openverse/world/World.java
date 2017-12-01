package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Block;
import xyz.upperlevel.openverse.world.chunk.*;

import java.util.ArrayDeque;
import java.util.Queue;

import static java.lang.Math.floor;
import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

@Getter
@Setter
public class World {
    private final String name;
    private ChunkPillarProvider chunkPillarProvider;

    public World(String name) {
        this.name = name;
        this.chunkPillarProvider = new SimpleChunkPillarProvider(this);
    }


    /**
     * Gets the {@link ChunkPillar} at the given chunk coordinates.
     */
    public ChunkPillar getChunkPillar(int x, int z) {
        return chunkPillarProvider.getChunkPillar(x, z);
    }

    /**
     * Sets the {@link ChunkPillar} at the given chunk coordinates to the given one.
     */
    public void setChunkPillar(ChunkPillar chunkPillar) {
        chunkPillarProvider.setChunkPillar(chunkPillar);
    }

    /**
     * Unloads the {@link ChunkPillar} at the given chunk coordinates (this implies inside chunks unloading).
     */
    public boolean unloadChunkPillar(int x, int z) {
        return chunkPillarProvider.unloadChunkPillar(x, z);
    }


    /**
     * Gets the {@link Chunk} at the given chunk coordinates.
     */
    public Chunk getChunk(int x, int y, int z) {
        return getChunkPillar(x, z).getChunk(y);
    }

    public Chunk getChunkFromBlock(int blockX, int blockY, int blockZ) {
        return getChunk(blockX >> 4, blockY >> 4, blockZ >> 4);
    }

    /**
     * Sets the {@link Chunk} at the given chunk coordinates to the given one.
     */
    public void setChunk(int x, int y, int z, Chunk chunk) {
        getChunkPillar(x, z).setChunk(y, chunk);
    }

    /**
     * Unloads the {@link Chunk} at the given chunk coordinates.
     */
    public boolean unloadChunk(int x, int y, int z) {
        return getChunkPillar(x, z).unloadChunk(y);
    }


    /**
     * Gets the {@link Block} at the given world coordinates.
     * It will create a new instance of the {@link Block} object.
     */
    public Block getBlock(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? null : chunk.getBlock(x & 0xF, y & 0xF, z & 0xF);
    }

    public Block getBlock(double x, double y, double z) {
        return getBlock((int) floor(x), (int) floor(y), (int) floor(z));
    }

    // Block state

    public BlockState getBlockState(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? AIR_STATE : chunk.getBlockState(x & 0xF, y & 0xF, z & 0xF);
    }

    public void setBlockState(int x, int y, int z, BlockState blockState) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk != null) {
            chunk.setBlockState(x & 0xF, y & 0xF, z & 0xF, blockState);
        }
    }

    // Block light

    public int getBlockLight(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? 0 : chunk.getBlockLight(x & 0xF, y & 0xF, z & 0xF);
    }

    public void setBlockLight(int x, int y, int z, int blockLight, boolean diffuse) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk != null) {
            chunk.setBlockLight(x & 0xF, y & 0xF, z & 0xF, blockLight, diffuse);
        }
    }

    // Block skylight

    public int getBlockSkylight(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? 0 : chunk.getBlockSkylight(x & 0xF, y & 0xF, z & 0xF);
    }

    public void setBlockSkylight(int x, int y, int z, int blockSkylight, boolean diffuse) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk != null) {
            chunk.setBlockSkylight(x & 0xF, y & 0xF, z & 0xF, blockSkylight, diffuse);
        }
    }
}