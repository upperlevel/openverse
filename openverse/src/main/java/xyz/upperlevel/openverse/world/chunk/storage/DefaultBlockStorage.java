package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;

public class DefaultBlockStorage extends BlockStorage {
    private BlockStateStorage data;

    public DefaultBlockStorage(Chunk chunk) {
        super(chunk);
        data = createStateStorage();
    }

    protected BlockStateStorage createStateStorage() {
        return new DefaultBlockStateStorage();
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return new Block(getChunk(), x, y, z, this);
    }

    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return data.get(x, y, z);
    }

    @Override
    public void setBlockState(int x, int y, int z, BlockState state) {
        data.set(x, y, z, state);
    }


}
