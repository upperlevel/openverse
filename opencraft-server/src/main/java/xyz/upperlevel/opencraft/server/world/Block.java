package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.common.world.BridgeBlockType;

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

    public BridgeBlockType getType() {
        return chunk.getType(chunkX, chunkY, chunkZ);
    }

    public void setType(BridgeBlockType type) {
        chunk.setType(type, chunkX, chunkY, chunkZ);
    }
}
