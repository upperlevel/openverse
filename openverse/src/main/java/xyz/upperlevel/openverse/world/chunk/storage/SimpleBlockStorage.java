package xyz.upperlevel.openverse.world.chunk.storage;

import lombok.Getter;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SimpleBlockStorage implements BlockStorage {
    private final Chunk chunk;

    private BlockStateStorage stateStorage;
    private BlockEntityStorage entityStorage;

    public SimpleBlockStorage(Chunk chunk) {
        this.chunk = chunk;
        stateStorage = createStateStorage();
        entityStorage = new SimpleBlockEntityStorage();
    }


    @Override
    public Block getBlock(int x, int y, int z) {
        return new Block(getChunk(), x, y, z, this);
    }


    protected BlockStateStorage createStateStorage() {
        return new SimpleBlockStateStorage();
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return stateStorage.get(x, y, z);
    }

    @Override
    public void setBlockState(int x, int y, int z, BlockState state) {
        stateStorage.set(x, y, z, state);
    }


    @Override
    public BlockEntity getBlockEntity(int x, int y, int z) {
        return entityStorage.get(x, y, z);
    }

    @Override
    public void setBlockEntity(int x, int y, int z, BlockEntity blockEntity) {
        entityStorage.set(x, y, z, blockEntity);
    }

    public Collection<BlockEntity> getBlockEntities() {
        return entityStorage.list();
    }
}
