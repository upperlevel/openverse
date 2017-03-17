package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;

public class LocalBlock {

    @Getter
    private LocalWorld world;
    
    @Getter
    private LocalChunk chunk;
    
    @Getter
    private int x, y, z;

    @Getter
    @Setter
    private BlockShape shape = null;

    public LocalBlock(LocalWorld world, int x, int y, int z) {
        this.world = world;
        this.chunk = world.getChunk(x, y, z);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public LocalBlock(LocalChunk chunk, int x, int y, int z) {
        this.world = chunk.getWorld();
        this.chunk = chunk;

        this.x = chunk.getX() * 16 + x;
        this.y = chunk.getY() * 16 + y;
        this.z = chunk.getZ() * 16 + z;
    }
}
