package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;

/**
 * A chunk pillar has a x and a z coordinate constant and provides for chunks along y axis.
 * Takes care of maximum block heights.
 */
@Getter
public class ChunkPillar {
    private final World world;
    private final int x, z;
    private VerticalChunkProvider verticalChunkProvider;

    private int heightmap[];
    private int heightmapMinimum;

    public ChunkPillar(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.verticalChunkProvider = new SimpleVerticalChunkProvider(this);

        // By default, each field of the heightmap is equal to max integer value
        this.heightmap = new int[256];
        for (int i = 0; i < heightmap.length; i++) {
            heightmap[i] = Integer.MAX_VALUE;
        }
        this.heightmapMinimum = Integer.MAX_VALUE;
    }

    public Chunk getChunk(int y) {
        return verticalChunkProvider.getChunk(y);
    }

    /**
     * Sets the chunk at the given height.
     * Diffuses the skylights and the block lights.
     */
    public void setChunk(int y, Chunk chunk) {
        verticalChunkProvider.setChunk(y, chunk);
    }

    public boolean hasChunk(int y) {
        return verticalChunkProvider.hasChunk(y);
    }

    public Chunk unloadChunk(int y) {
        return verticalChunkProvider.unloadChunk(y);
    }

    public void setBlockState(int x, int y, int z, BlockState blockState) {
        if (getHeight(x, z) > y)
            setHeight(x, z, y);
        // todo sets block in chunk
    }

    /**
     * Gets the height at the given location.
     *
     * @param x the x-axis location
     * @param z the z-axis location
     * @return the height
     */
    public int getHeight(int x, int z) {
        return heightmap[x << 4 | z];
    }

    /**
     * Sets the height at the given location to the given value.
     *
     * @param x      the x-axis location
     * @param z      the z-axis location
     * @param height the height
     */
    public void setHeight(int x, int z, int height) {
        heightmap[x << 4 | z] = height;
        if (height < heightmapMinimum) {
            heightmapMinimum = height;
        }
    }

    /**
     * Sets the heightmap to the given one.
     *
     * @param heightmap the heightmap
     */
    public void setHeightmap(int[] heightmap) {
        if (heightmap.length != 256) {
            throw new IllegalArgumentException("An heightmap must be 256 long");
        }
        this.heightmap = heightmap;
        for (int i = 0; i < 256; i++) {
            if (heightmap[i] < heightmapMinimum) {
                heightmapMinimum = heightmap[i];
            }
        }
    }

    public static long hash(int x, int z) {
        return ((long) x << 32) | ((long) z) & 0xFFFF_FFFFL;
    }
}
