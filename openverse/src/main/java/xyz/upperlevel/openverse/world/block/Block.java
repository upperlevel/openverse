package xyz.upperlevel.openverse.world.block;

import lombok.AccessLevel;
import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;

@Getter
public class Block {
    private final World world;
    private final int x, y, z;
    private final Chunk chunk;
    private final BlockStorage storage;

    @Getter(AccessLevel.NONE)
    private final int rx, ry, rz;

    public Block(Chunk chunk, int x, int y, int z, BlockStorage storage) {
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.rx = x;
        this.ry = y;
        this.rz = z;
        this.x = chunk.getX() * Chunk.WIDTH + x;
        this.y = chunk.getY() * Chunk.HEIGHT + y;
        this.z = chunk.getZ() * Chunk.LENGTH + z;
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
        return storage.getBlockType(rx, ry, rz);
    }

    public void setType(BlockType type) {
        storage.setBlockType(rx, ry, rz, type);
    }

    public BlockState getState() {
        return storage.getBlockState(rx, ry, rz);
    }

    public void setState(BlockState state) {
        storage.setBlockState(rx, ry, rz, state);
    }
}