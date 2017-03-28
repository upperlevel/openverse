package xyz.upperlevel.opencraft.world;

public interface Chunk {

    World getWorld();

    int getX();

    int getY();

    int getZ();

    ChunkData getData();

    Block getBlock(int x, int y, int z);
}