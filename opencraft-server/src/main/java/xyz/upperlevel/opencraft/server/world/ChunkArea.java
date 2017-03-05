package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;

public class ChunkArea {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    public static final int SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private BlockType[][][] types = new BlockType[WIDTH][HEIGHT][LENGTH];

    public ChunkArea() {
    }

    public BlockType getBlock(int x, int y, int z) {
        return types[x][y][z] == null ? BlockType.create("null_shape") : types[x][y][z];
    }

    public ChunkArea setBlock(int x, int y, int z, BlockType type) {
        types[x][y][z] = type;
        return this;
    }
}