package xyz.upperlevel.opencraft.common;

public interface Block {

    default World getWorld() {
        return getChunk().getWorld();
    }

    Chunk getChunk();

    BlockType getType();

    void setType(BlockType type);
}
