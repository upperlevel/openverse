package xyz.upperlevel.openverse.world.block.tileentity;

import xyz.upperlevel.openverse.world.block.BlockType;


public interface TileEntity {
    /**
     * Gets the {@link BlockType} that declared this entity.
     * @return the {@link BlockType} that declared this entity
     */
    BlockType getBlockType();
}
