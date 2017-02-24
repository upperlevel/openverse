package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;

import java.util.Objects;

public class Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16, SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private World world;

    @Getter
    private int x, y, z;

    @Getter
    private BlockType[][][] blocks = new BlockType[WIDTH][HEIGHT][LENGTH];

    public Chunk(World world, int x, int y, int z) {
        Objects.requireNonNull(world, "world");
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final int getWidth() {
        return WIDTH;
    }

    public final int getHeight() {
        return HEIGHT;
    }

    public final int getLength() {
        return LENGTH;
    }

    public final int getSize() {
        return SIZE;
    }

    public void generate() {
        world.getChunkGenerator().generate(this);
    }

    public BlockType getType(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public void setType(BlockType type, int x, int y, int z) {
        blocks[x][y][z] = type;
    }

    public Block getBlock(int x, int y, int z) {
        return new Block(this, x, y, z);
    }

    public int toWorldX(int chunkX) {
        return x * WIDTH + chunkX;
    }

    public double toWorldX(double chunkX) {
        return x * WIDTH + chunkX;
    }

    public int toWorldY(int chunkY) {
        return y * HEIGHT + chunkY;
    }

    public double toWorldY(double chunkY) {
        return y * HEIGHT + chunkY;
    }

    public int toWorldZ(int chunkZ) {
        return z * LENGTH + chunkZ;
    }

    public double toWorldZ(double chunkZ) {
        return z * LENGTH + chunkZ;
    }
}
