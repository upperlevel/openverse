package xyz.upperlevel.openverse.world.chunk.storage;

import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import java.util.Collection;

public interface BlockStorage {
    Chunk getChunk();

    default World getWorld() {
        return getChunk().getWorld();
    }


    Block getBlock(int x, int y, int z);


    default BlockType getBlockType(int x, int y, int z) {
        return getBlockState(x, y, z).getBlockType();
    }

    default void setBlockType(int x, int y, int z, BlockType type) {
        setBlockState(x, y, z, type.getDefaultBlockState());
    }

    
     BlockState getBlockState(int x, int y, int z);

     void setBlockState(int x, int y, int z, BlockState state);
    
    
     BlockEntity getBlockEntity(int x, int y, int z);
    
     void setBlockEntity(int x, int y, int z, BlockEntity entity);
    
     Collection<BlockEntity> getBlockEntities();
}
