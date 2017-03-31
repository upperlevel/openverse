package xyz.upperlevel.opencraft.world;

import lombok.NonNull;
import xyz.upperlevel.opencraft.resource.block.BlockType;

public interface ChunkData {

    BlockType getType(int x, int y, int z);

    void setType(int x, int y, int z, @NonNull BlockType type);
}