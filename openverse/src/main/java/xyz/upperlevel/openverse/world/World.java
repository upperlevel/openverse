package xyz.upperlevel.openverse.world;

public interface World {

    String getName();

    Chunk getChunk(int x, int y, int z);

    Block getBlock(int x, int y, int z);
}