package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import lombok.NonNull;

public class Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    @Getter
    private World world;

    @Getter
    private int x, y, z;

    @Getter
    private BlockType[][][] voxels = new BlockType[WIDTH][HEIGHT][LENGTH];

    public Chunk(@NonNull World world, int x, int y, int z) {
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

    public BlockType get(int x, int y, int z) {
        return voxels[x][y][z];
    }

    public void set(int x, int y, int z, BlockType voxel) {
        voxels[x][y][z] = voxel;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chunk) {
            Chunk c = (Chunk) o;
            return world.equals(c.getWorld()) && x == c.getX() && y == c.getY() && z == c.getZ();
        }
        return false;
    }
}
