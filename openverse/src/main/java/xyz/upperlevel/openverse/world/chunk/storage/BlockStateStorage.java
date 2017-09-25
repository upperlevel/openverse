package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.state.BlockState;

public interface BlockStateStorage {
    BlockState getBlockState(int x, int y, int z);

    void setBlockState(int x, int y, int z, BlockState state);
}
