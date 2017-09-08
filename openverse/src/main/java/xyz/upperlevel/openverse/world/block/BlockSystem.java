package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;

public abstract class BlockSystem {

    @Getter
    private final World world;

    @Getter
    private final Chunk chunk;

    public BlockSystem(@NonNull Chunk chunk) {
        this.chunk = chunk;
        this.world = chunk.getWorld();
    }

    public abstract Block getBlock(int x, int y, int z);

    public abstract BlockType getBlockType(int x, int y, int z);
    public abstract void setBlockType(int x, int y, int z, BlockType type);
}
