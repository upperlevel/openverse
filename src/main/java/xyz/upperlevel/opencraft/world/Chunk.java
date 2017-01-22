package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.opencraft.world.block.BlockState;

@RequiredArgsConstructor
public class Chunk {

    public static final int
            WIDTH = 16,
            HEIGHT = 16,
            LENGTH = 16;

    @Getter
    public final World world;

    @Getter
    public final int x, y, z;

    @Getter
    public final ChunkCache cache = new ChunkCache(this);

    public boolean isLoaded() {
        return world.isLoaded(this);
    }

    public void load() {
        if (!isLoaded()) {
            world.loadChunk(this);
            world.getChunkGenerator().generate(this, x, y, z);
        }
    }

    public void unload() {
        if (isLoaded())
            world.unloadChunk(this);
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

    //<editor-fold desc="world coords -> chunk coords">
    public int toChunkX(int x) {
        return x % WIDTH;
    }

    public double toChunkX(double x) {
        return x % WIDTH;
    }

    public int toChunkY(int y) {
        return y % HEIGHT;
    }

    public double toChunkY(double y) {
        return y % HEIGHT;
    }

    public int toChunkZ(int z) {
        return z % LENGTH;
    }

    public double toChunkZ(double z) {
        return z % LENGTH;
    }
    //</editor-fold>

    //<editor-fold desc="chunk coords -> world coords">
    public int toWorldX(int x) {
        return this.x * WIDTH + x;
    }

    public double toWorldX(double x) {
        return this.x * WIDTH + x;
    }

    public int toWorldY(int y) {
        return this.y * HEIGHT + y;
    }

    public double toWorldY(double y) {
        return this.y * HEIGHT + y;
    }

    public int toWorldZ(int z) {
        return this.z * LENGTH + z;
    }

    public double toWorldZ(double z) {
        return this.z * LENGTH + z;
    }
    //</editor-fold>

    public Block getBlock(int x, int y, int z) {
        return cache.getBlock(x, y, z);
    }

    public BlockState getBlockState(int x, int y, int z) {
        return cache.getBlockState(x, y, z);
    }

    public void setBlockState(int x, int y, int z, BlockState state) {
        cache.setBlockState(x, y, z, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chunk) {
            Chunk chunk = (Chunk) obj;
            return chunk.world.equals(world) &&
                    chunk.x == x &&
                    chunk.y == y &&
                    chunk.z == z;
        }
        return false;
    }
}