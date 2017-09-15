package xyz.upperlevel.openverse.world.block;

import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;

@Getter
public class Block {
    private final World world;
    private final int rx, ry, rz;
    private final int x, y, z;
    private final Chunk chunk;
    private final BlockStorage parent;

    public Block(Chunk chunk, int x, int y, int z, BlockStorage parent) {
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.rx = x;
        this.ry = y;
        this.rz = z;
        this.x = chunk.getX() * Chunk.WIDTH  + x;
        this.y = chunk.getY() * Chunk.HEIGHT + y;
        this.z = chunk.getZ() * Chunk.LENGTH + z;
        this.parent = parent;
    }

    public Block getRelative(int x, int y, int z) {
        return world.getBlock(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    public BlockType getType() {
        return parent.getBlockType(rx, ry, rz);
    }

    public void setType(BlockType type) {
        parent.setBlockType(rx, ry, rz, type);
    }

    public BlockState getState() {
        return parent.getBlockState(x, y, z);
    }

    public void setState(BlockState state) {
        parent.setBlockState(x, y, z, state);
    }
}