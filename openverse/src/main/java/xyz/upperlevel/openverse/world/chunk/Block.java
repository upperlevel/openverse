package xyz.upperlevel.openverse.world.chunk;

import lombok.AccessLevel;
import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;

@Getter
public class Block {
    private final World world;
    private final int chunkRelativeX, chunkRelativeY, chunkRelativeZ, x, y, z;
    private final Chunk chunk;
    private final BlockStorage storage;

    public Block(Chunk chunk, int chunkRelativeX, int chunkRelativeY, int chunkRelativeZ, BlockStorage storage) {
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.chunkRelativeX = chunkRelativeX;
        this.chunkRelativeY = chunkRelativeY;
        this.chunkRelativeZ = chunkRelativeZ;
        this.x = chunk.getX() * 16 + chunkRelativeX;
        this.y = chunk.getY() * 16 + chunkRelativeY;
        this.z = chunk.getZ() * 16 + chunkRelativeZ;
        this.storage = storage;
    }

    public Block getRelative(int x, int y, int z) {
        return world.getBlock(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }


    public BlockType getType() {
        return storage.getBlockType(chunkRelativeX, chunkRelativeY, chunkRelativeZ);
    }

    public void setType(BlockType type) {
        storage.setBlockType(chunkRelativeX, chunkRelativeY, chunkRelativeZ, type);
    }


    public BlockState getState() {
        return storage.getBlockState(chunkRelativeX, chunkRelativeY, chunkRelativeZ);
    }

    public void setState(BlockState state) {
        storage.setBlockState(chunkRelativeX, chunkRelativeY, chunkRelativeZ, state);
    }
}