package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.world.block.Block;
import xyz.upperlevel.openverse.world.block.BlockSystem;
import xyz.upperlevel.openverse.world.block.DefaultBlockSystem;
import xyz.upperlevel.openverse.world.World;

//TODO: use one-dimensional array for better performance
public class Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16;

    @Getter
    private final World world;

    //The chunk-coordinates (to translate to blocks you must do x * 16 or x << 4)
    @Getter
    private final ChunkLocation location;

    @Getter
    private final BlockSystem blockSystem;

    public Chunk(@NonNull World world, @NonNull ChunkLocation location) {
        this.world = world;
        this.location = location;
        blockSystem = new DefaultBlockSystem(this);
    }

    public Chunk(@NonNull World world, int x, int y, int z) {
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
        return blockSystem.getBlock(x, y, z);
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blockSystem.getBlockType(x, y, z);
    }

    public void setBlockType(int x, int y, int z, BlockType type) {
        blockSystem.setBlockType(x, y, z, type);
    }
}