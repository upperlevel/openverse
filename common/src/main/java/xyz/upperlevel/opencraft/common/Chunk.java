package xyz.upperlevel.opencraft.common;


import lombok.Getter;
import lombok.NonNull;

public abstract class Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    @Getter
    @NonNull
    private final World world;

    @Getter
    @NonNull
    private final int x, y, z;

    public Chunk(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getLength() {
        return LENGTH;
    }

    public int getSize() {
        return getWidth() * getHeight() * getLength();
    }

    public abstract Block getBlock(int x, int y, int z);

    public abstract void setBlock(int x, int y, int z, BlockType type);
}
