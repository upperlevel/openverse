package xyz.upperlevel.openverse.world;

import xyz.upperlevel.openverse.resource.block.BlockType;

public interface Block {

    World getWorld();

    int getX();

    int getY();

    int getZ();

    Chunk getChunk();

    default Block getRelative(int x, int y, int z) {
        return getWorld().getBlock(
                getX() + x,
                getY() + y,
                getZ() + z
        );
    }

    BlockType getType();

    void setType(BlockType type);
}