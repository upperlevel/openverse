package xyz.upperlevel.opencraft.common.world;

import xyz.upperlevel.opencraft.common.block.BlockType;

public interface Block {

    World getWorld();

    Chunk getChunk();

    BlockType getType();

    void setType(BlockType type);
}
