package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.block.BlockType;

public interface ChunkData {

    BlockType getType(int x, int y, int z);

    void setType(int x, int y, int z, BlockType type);
}