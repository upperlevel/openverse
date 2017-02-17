package xyz.upperlevel.opencraft.common.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.common.block.BlockType;
import xyz.upperlevel.opencraft.common.block.BlockState;

import java.util.Objects;

public class Block {

    @Getter
    private World world;

    @Getter
    private Chunk chunk;

    @Getter
    private int x, y, z;

    @Getter
    private int chunkBlockX, chunkBlockY, chunkBlockZ;

    public Block(Chunk chunk, int chunkBlockX, int chunkBlockY, int chunkBlockZ) {
        Objects.requireNonNull(chunk, "chunk");
        world = chunk.getWorld();
        this.chunk = chunk;

        this.chunkBlockX = chunkBlockX;
        this.chunkBlockY = chunkBlockY;
        this.chunkBlockZ = chunkBlockZ;
    }

    public BlockType getType() {
        return getState().getType();
    }

    public Block setType(BlockType type) {
        chunk.setBlockType(type, chunkBlockX, chunkBlockY, chunkBlockZ);
        return this;
    }

    public BlockState getState() {
        return chunk.getBlockState(chunkBlockX, chunkBlockY, chunkBlockZ);
    }

    public Block setState(BlockState state) {
        chunk.setBlockState(state, chunkBlockX, chunkBlockY, chunkBlockZ);
        return this;
    }
}
