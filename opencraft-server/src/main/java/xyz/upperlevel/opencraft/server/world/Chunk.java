package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.common.world.BridgeBlockType;
import xyz.upperlevel.opencraft.common.world.ChunkArea;

import java.util.Objects;

public class Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16, SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private World world;

    @Getter
    private int x, y, z;

    @Getter
    private ChunkArea area = new ChunkArea();

    public Chunk(World world, int x, int y, int z) {
        Objects.requireNonNull(world, "world");
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        generate();
    }

    public void generate() {
        world.getChunkGenerator().generate(this);
    }

    public BridgeBlockType getType(int x, int y, int z) {
        return area.getBlock(x, y, z);
    }

    public void setType(BridgeBlockType type, int x, int y, int z) {
        area.setBlock(x, y, z, type);
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
