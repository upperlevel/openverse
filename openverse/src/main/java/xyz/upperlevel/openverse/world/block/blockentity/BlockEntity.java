package xyz.upperlevel.openverse.world.block.blockentity;

import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;


public interface BlockEntity {
    /**
     * Gets the {@link BlockType} the type of the block that declared this entity
     * @return the {@link BlockType} the type of the block
     */
    default BlockType getBlockType() {
        return getBlock().getType();
    }

    /**
     * Gets the {@link BlockState} the state of the block that declared this entity.
     * @return the {@link BlockState} the state of the block
     */
    default BlockState getBlockState() {
        return getBlock().getState();
    }

    /**
     * Gets the {@link Block} that declared this entity.
     * @return the {@link Block}
     */
    Block getBlock();
}
