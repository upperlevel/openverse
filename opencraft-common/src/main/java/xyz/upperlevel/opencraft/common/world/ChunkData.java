package xyz.upperlevel.opencraft.common.world;

import xyz.upperlevel.opencraft.common.block.BlockType;

public interface ChunkData {

    BlockType getType(int x, int y, int z);

    void setType(int x, int y, int z, BlockType type);
}