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

    private int heightMap[];
    private boolean heightMapGenerated;

    public ChunkPillar(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.heightMap = new int[16 * 16];
        for (int i = 0; i < heightMap.length; i++)
            heightMap[i] = Integer.MIN_VALUE;
        this.verticalChunkProvider = new SimpleVerticalChunkProvider(this);
    }

    public Chunk getChunk(int y) {
        return verticalChunkProvider.getChunk(y);
    }

    public void setChunk(int y, Chunk chunk) {
        verticalChunkProvider.setChunk(y, chunk);
    }

    public boolean hasChunk(int y) {
        return verticalChunkProvider.hasChunk(y);
    }

    public boolean unloadChunk(int y) {
        return verticalChunkProvider.unloadChunk(y);
    }

    public int getHeight(int x, int z) {
        return heightMap[x << 4 | z];
    }

    public void setHeight(int raw, int height) {
        heightMap[raw] = height;
    }

    public void setHeight(int x, int z, int height) {
        heightMap[x << 4 | z] = height;
    }

    public void setHeightMapGenerated(boolean heightMapGenerated) {
        this.heightMapGenerated = heightMapGenerated;
    }

    public void setBlockState(int x, int y, int z, BlockState blockState) {
        if (getHeight(x, z) > y)
            setHeight(x, z, y);
        // todo sets block in chunk
    }
}
