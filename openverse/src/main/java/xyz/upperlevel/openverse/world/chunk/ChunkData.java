package xyz.upperlevel.openverse.world.chunk;

import lombok.NonNull;
import xyz.upperlevel.openverse.resource.BlockType;

public interface ChunkData {

    BlockType getType(int x, int y, int z);

    void setType(int x, int y, int z, @NonNull BlockType type);
}