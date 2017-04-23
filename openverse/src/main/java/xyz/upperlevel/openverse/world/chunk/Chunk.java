package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
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
    private final int x, y, z;

    protected Block blocks[][][] = new Block[WIDTH][HEIGHT][LENGTH];

    @Getter
    @Setter
    private ChunkData data;

    public Chunk(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void regenerate() {
        world.getGenerator().generate(this);
    }

    public Chunk getRelative(int x, int y, int z) {
        return getWorld().getChunk(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }
}