package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Block;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import java.util.Collection;

public interface BlockStorage {
    BlockState AIR_STATE = BlockType.AIR.getDefaultBlockState();

    World getWorld();

    Chunk getChunk();


    Block getBlock(int x, int y, int z);


    BlockState getBlockState(int x, int y, int z);

    void setBlockState(int x, int y, int z, BlockState blockState);


    BlockEntity getBlockEntity(int x, int y, int z);

    void setBlockEntity(int x, int y, int z, BlockEntity blockEntity);

    Collection<BlockEntity> getBlockEntities();


    int getBlockLight(int x, int y, int z);

    void setBlockLight(int x, int y, int z, int blockLight);


    int getBlockSkyLight(int x, int y, int z);

    void setBlockSkyLight(int x, int y, int z, int blockSkyLight);
}
