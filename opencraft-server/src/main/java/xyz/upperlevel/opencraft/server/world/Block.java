package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;

import java.util.Objects;

public class Block {

    @Getter
    private World world;

    @Getter
    private Chunk chunk;

    @Getter
    private int x, y, z;

    @Getter
    private int chunkX, chunkY, chunkZ;

    public Block(Chunk chunk, int chunkX, int chunkY, int chunkZ) {
        Objects.requireNonNull(chunk, "chunk");
        world = chunk.getWorld();
        this.chunk = chunk;

        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;

        x = chunk.toWorldX(chunkX);
        y = chunk.toWorldY(chunkY);
        z = chunk.toWorldZ(chunkZ);
    }

    public BlockType getType() {
        return getState().getType();
    }

    public Block setType(BlockType type) {
        chunk.setBlockType(type, chunkX, chunkY, chunkZ);
        return this;
    }

    public BlockState getState() {
        return chunk.getBlockState(chunkX, chunkY, chunkZ);
    }

    public Block setState(BlockState state) {
        chunk.setBlockState(state, chunkX, chunkY, chunkZ);
        return this;
    }
}
