package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.openverse.world.chunk.storage.SimpleBlockStorage;

@Getter
public class Chunk {
    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    private final World world;
    private final ChunkLocation location;
    private final BlockStorage blockStorage;

    public Chunk(World world, ChunkLocation location) {
        this.world = world;
        this.location = location;
        this.blockStorage = new SimpleBlockStorage(this);
    }

    public Chunk(World world, int x, int y, int z) {
        this(world, new ChunkLocation(x, y, z));
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public int getZ() {
        return location.z;
    }

    public Chunk getRelative(int x, int y, int z) {
        return getWorld().getChunk(
                location.x + x,
                location.y + y,
                location.z + z
        );
    }

    public Block getBlock(int x, int y, int z) {
        return blockStorage.getBlock(x, y, z);
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blockStorage.getBlockType(x, y, z);
    }

    public void setBlockType(int x, int y, int z, BlockType type) {
        blockStorage.setBlockType(x, y, z, type);
    }
}