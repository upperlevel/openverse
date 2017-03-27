package xyz.upperlevel.opencraft.common.world;

public interface Chunk {

    World getWorld();

    int getX();

    int getY();

    int getZ();

    ChunkData getData();

    Block getBlock(int x, int y, int z);
}