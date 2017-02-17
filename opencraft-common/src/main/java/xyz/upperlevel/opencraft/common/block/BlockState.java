package xyz.upperlevel.opencraft.common.block;

public interface BlockState {

    BlockState NULL = () -> BlockType.NULL;

    BlockType getType();
}
