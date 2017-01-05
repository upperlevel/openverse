package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Chunk implements BlockArea<Block> {

    public static final int
            WIDTH  = 16,
            HEIGHT = 16,
            LENGTH = 16;

    @Getter public final World world;
    @Getter public final int x, y, z;

    @Getter public final ChunkCache cache = new ChunkCache(this);

    public boolean isLoaded() {
        return world.isLoaded(this);
    }

    public void load() {
        if (!isLoaded()) {
            world.loadChunk(this);
            world.getChunkGenerator().generate(cache, x, y, z);
        }
    }

    public void unload() {
        if (isLoaded())
            world.unloadChunk(this);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    public double asWorldX(double x) {
        return WIDTH * this.x + x;
    }

    public double asWorldY(double y) {
        return HEIGHT * this.y + y;
    }

    public double asWorldZ(double z) {
        return LENGTH * this.z + z;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return cache.getBlock(x, y, z);
    }
}