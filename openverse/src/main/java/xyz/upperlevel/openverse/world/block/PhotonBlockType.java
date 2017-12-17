package xyz.upperlevel.openverse.world.block;

import xyz.upperlevel.openverse.world.block.state.BlockState;

public class PhotonBlockType extends BlockType {
    public PhotonBlockType() {
        super("photon");
    }

    @Override
    public int getEmittedBlockLight(BlockState blockState) {
        return 16;
    }
}
