package xyz.upperlevel.opencraft.common.network;

import lombok.Getter;

public class ChunkArea {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    public static final int SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private CBlockType[][][] types = new CBlockType[WIDTH][HEIGHT][LENGTH];

    public ChunkArea() {
    }

    public CBlockType getBlock(int x, int y, int z) {
        return types[x][y][z] == null ? CBlockType.create("null_block") : types[x][y][z];
    }

    public ChunkArea setBlock(int x, int y, int z, CBlockType type) {
        types[x][y][z] = type;
        return this;
    }
}