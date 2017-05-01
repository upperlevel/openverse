package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.world.Block;
import xyz.upperlevel.openverse.world.World;

//TODO: use one-dimensional array for better performance
public class Chunk {

    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;
    public static final int LENGTH = 16;

    @Getter
    private final World world;

    //The chunk-coordinates (to translate to blocks you must do x * 16 or x << 4)
    @Getter
    private final ChunkLocation location;

    protected Block blocks[][][] = new Block[WIDTH][HEIGHT][LENGTH];

    @Getter
    protected final ChunkData data;

    public Chunk(@NonNull ChunkData data, @NonNull World world, @NonNull ChunkLocation location) {
        this.data = data;
        this.world = world;
        this.location = location;
    }

    public Chunk(@NonNull ChunkData data, @NonNull World world, int x, int y, int z) {
        this(data, world, new ChunkLocation(x, y, z));
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
        return blocks[x][y][z];
    }
}