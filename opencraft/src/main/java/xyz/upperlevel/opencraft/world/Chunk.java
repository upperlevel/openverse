package xyz.upperlevel.opencraft.world;

public interface Chunk {

    int WIDTH = 16;

    int HEIGHT = 16;

    int LENGTH = 16;

    World getWorld();

    int getX();

    int getY();

    int getZ();

    ChunkData getData();

    default Chunk getRelative(int x, int y, int z) {
        return getWorld().getChunk(
                getX() + x,
                getY() + y,
                getZ() + z
        );
    }

    Block getBlock(int x, int y, int z);
}