package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.block.BlockType;

public interface Block {

    World getWorld();

    Chunk getChunk();

    BlockType getType();

    void setType(BlockType type);
}
