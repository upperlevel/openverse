package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.state.BlockState;

public interface BlockStateStorage {
    BlockState get(int x, int y, int z);

    void set(int x, int y, int z, BlockState state);
}
