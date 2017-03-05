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

        x = chunk.getX() * 16 + chunkX;
        y = chunk.getY() * 16 + chunkY;
        z = chunk.getZ() * 16 + chunkZ;
    }

    public BlockType getType() {
        return chunk.getType(chunkX, chunkY, chunkZ);
    }

    public void setType(BlockType type) {
        chunk.setType(type, chunkX, chunkY, chunkZ);
    }
}