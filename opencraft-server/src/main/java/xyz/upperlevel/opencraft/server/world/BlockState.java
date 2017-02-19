package xyz.upperlevel.opencraft.server.world;

public interface BlockState {

    BlockState NULL = () -> BlockType.NULL;

    BlockType getType();
}
