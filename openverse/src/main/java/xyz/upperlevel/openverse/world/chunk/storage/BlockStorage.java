package xyz.upperlevel.openverse.world.chunk.storage;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;

public abstract class BlockStorage {
    @Getter
    private final World world;

    @Getter
    private final Chunk chunk;

    public BlockStorage(@NonNull Chunk chunk) {
        this.chunk = chunk;
        this.world = chunk.getWorld();
    }

    public abstract Block getBlock(int x, int y, int z);

    public BlockType getBlockType(int x, int y, int z) {
        return getBlockState(x, y, z).getBlockType();
    }

    public void setBlockType(int x, int y, int z, BlockType type) {
        setBlockState(x, y, z, type.getDefaultBlockState());
    }

    public abstract BlockState getBlockState(int x, int y, int z);

    public abstract void setBlockState(int x, int y, int z, BlockState state);
}
