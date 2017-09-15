package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SimpleBlockEntityStorage implements BlockEntityStorage {
    private Map<Integer, BlockEntity> blockEntities = new HashMap<>();

    private static int getIndex1d(int x, int y, int z) {
        return x << 8 | y << 4 | z;
    }

    @Override
    public BlockEntity get(int x, int y, int z) {
        return blockEntities.get(getIndex1d(x, y, z));
    }

    @Override
    public void set(int x, int y, int z, BlockEntity entity) {
        blockEntities.put(getIndex1d(x, y, z), entity);
    }

    @Override
    public Collection<BlockEntity> list() {
        return blockEntities.values();
    }
}
