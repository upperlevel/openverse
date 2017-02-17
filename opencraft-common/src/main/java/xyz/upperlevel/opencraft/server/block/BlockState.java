package xyz.upperlevel.opencraft.server.block;

public interface BlockState {

    BlockState NULL = () -> BlockType.NULL;

    BlockType getType();
}
