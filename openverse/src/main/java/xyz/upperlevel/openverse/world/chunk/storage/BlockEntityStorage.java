package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;

import java.util.Collection;

public interface BlockEntityStorage {
    BlockEntity get(int x, int y, int z);

    void set(int x, int y, int z, BlockEntity entity);

    Collection<BlockEntity> list();
}
