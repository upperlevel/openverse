package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;

@Getter
@Setter
public class Block {
    private final World world;
    private final int x, y, z;
    private final Chunk chunk;
    private BlockType type;

    public Block(Chunk chunk, int x, int y, int z) {
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.x = chunk.getX() * Chunk.WIDTH  + x;
        this.y = chunk.getY() * Chunk.HEIGHT + y;
        this.z = chunk.getZ() * Chunk.LENGTH + z;
    }

    public Block getRelative(int x, int y, int z) {
        return getWorld().getBlock(
                getX() + x,
                getY() + y,
                getZ() + z
        );
    }
}