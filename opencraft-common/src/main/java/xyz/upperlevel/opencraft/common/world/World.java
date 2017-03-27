package xyz.upperlevel.opencraft.common.world;

public interface World {

    Chunk getChunk(int x, int y, int z);

    Block getBlock(int x, int y, int z);
}