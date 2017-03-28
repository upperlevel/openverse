package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.block.BlockType;

public class ChunkStorer {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    @Getter
    private BlockType[][][] voxels = new BlockType[WIDTH][HEIGHT][LENGTH];

    public ChunkStorer() {
    }

    public BlockType getType(int x, int y, int z) {
        return voxels[x][y][z];
    }

    public void setVoxel(int x, int y, int z, BlockType type) {
        voxels[x][y][z] = type;
    }
}