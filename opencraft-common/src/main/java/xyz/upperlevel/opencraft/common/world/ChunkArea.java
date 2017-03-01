package xyz.upperlevel.opencraft.common.world;

import lombok.Getter;

public class ChunkArea {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    public static final int SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private BridgeBlockType[][][] types = new BridgeBlockType[WIDTH][HEIGHT][LENGTH];

    public ChunkArea() {
    }

    public BridgeBlockType getBlock(int x, int y, int z) {
        return types[x][y][z] == null ? BridgeBlockType.create("null_block") : types[x][y][z];
    }

    public ChunkArea setBlock(int x, int y, int z, BridgeBlockType type) {
        types[x][y][z] = type;
        return this;
    }
}