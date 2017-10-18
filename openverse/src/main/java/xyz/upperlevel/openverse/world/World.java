package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.util.math.Aabb3d;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.*;
import xyz.upperlevel.openverse.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import static xyz.upperlevel.openverse.util.math.MathUtil.ceili;
import static xyz.upperlevel.openverse.util.math.MathUtil.floori;
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
    public ChunkPillar getChunkPillar(int x, int z, boolean load) {
        return chunkPillarProvider.getChunkPillar(x, z, load);
    }

    public ChunkPillar getChunkPillar(int x, int z) {
        return chunkPillarProvider.getChunkPillar(x, z, true);
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

    public boolean isChunkLoaded(int x, int y, int z) {
        return chunkPillarProvider.hasChunk(x, y, z);
    }

    public boolean isBlockLoaded(int x, int y, int z) {
        return chunkPillarProvider.hasChunk(x >> 4, y, z >> 4);
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
        return getBlock(floori(x), floori(y), floori(z));
    }

    public List<Aabb3d> getBlockCollisions(Entity entity, Aabb3d box) {
        List<Aabb3d> res = new ArrayList<>();

        final int minX = floori(box.minX) - 1;
        final int minY = floori(box.minY) - 1;
        final int minZ = floori(box.minZ) - 1;
        final int maxX = ceili(box.maxX) + 1;
        final int maxY = ceili(box.maxY) + 1;
        final int maxZ = ceili(box.maxZ) + 1;

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                ChunkPillar pillar = getChunkPillar(x >> 4, z >> 4, false);
                if (pillar == null) {
                    //Chunk not loaded
                    continue;
                }
                for (int y = minY; y < maxY; y++) {
                    BlockState state = pillar.getChunk(y << 4).getBlockState(x & 15, y & 15, z & 15);
                    if (state == AIR_STATE) {
                        //Block is air
                        continue;
                    }
                    state.getBlockType().addCollisionBoxes(state, entity, x, y, z, box, res);
                }
            }
        }

        return res;
    }


    /**
     * Gets the {@link BlockState} at the given coordinates.
     */
    public BlockState getBlockState(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? AIR_STATE : chunk.getBlockState(x & 0xF, y & 0xF, z & 0xF);
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     */
    public void setBlockState(int x, int y, int z, BlockState blockState) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk != null)
            chunk.setBlockState(x & 0xF, y & 0xF, z & 0xF, blockState);
    }
}