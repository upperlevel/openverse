package xyz.upperlevel.opencraft.common.world;

import lombok.Getter;

public class CommonChunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    public static final int SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private int x, y, z;

    @Getter
    private CommonBlockType[][][] types = new CommonBlockType[WIDTH][HEIGHT][LENGTH];

    public CommonChunk(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CommonBlockType getBlock(int x, int y, int z) {
        return types[x][y][z];
    }

    public CommonChunk setBlock(int x, int y, int z, CommonBlockType type) {
        types[x][y][z] = type;
        return this;
    }
}