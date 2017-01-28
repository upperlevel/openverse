package xyz.upperlevel.opencraft.world;

import lombok.Getter;

<<<<<<< HEAD
=======
import java.util.Objects;

>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026
public class Chunk {

    public static final int
            WIDTH = 16,
            HEIGHT = 16,
            LENGTH = 16;

    @Getter
    public final World world;

    @Getter
    public final int x, y, z;

    @Getter
    public final Block[][][] blocks = new Block[WIDTH][HEIGHT][LENGTH];
<<<<<<< HEAD

    Chunk(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        fillEmpty();
    }
=======
>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026

    Chunk(World world, int chunkX, int chunkY, int chunkZ) {
        Objects.requireNonNull(world, "World cannot be null.");
        this.world = world;
        x = chunkX;
        y = chunkY;
        z = chunkZ;
        // fills block array by instantiates all its blocks
        fillEmpty();
    }

    public final int getWidth() {
        return WIDTH;
    }

    public final int getHeight() {
        return HEIGHT;
    }

    public final int getLength() {
        return LENGTH;
    }

    public Chunk getRelative(int x, int y, int z) {
        return world.getChunk(
                this.x + x,
                this.y + y,
                this.z + z
        );
    }

    public Chunk getRelative(ChunkRelative relative) {
        return getRelative(
                relative.offsetX,
                relative.offsetY,
                relative.offsetZ
        );
    }

    //<editor-fold desc="world coords -> chunk coords">
    public int toChunkX(int x) {
        return x % WIDTH;
    }

    public double toChunkX(double x) {
        return x % WIDTH;
    }

    public int toChunkY(int y) {
        return y % HEIGHT;
    }

    public double toChunkY(double y) {
        return y % HEIGHT;
    }

    public int toChunkZ(int z) {
        return z % LENGTH;
    }

    public double toChunkZ(double z) {
        return z % LENGTH;
    }
    //</editor-fold>

    //<editor-fold desc="chunk coords -> world coords">
    public int toWorldX(int x) {
        return this.x * WIDTH + x;
    }

    public double toWorldX(double x) {
        return this.x * WIDTH + x;
    }

    public int toWorldY(int y) {
        return this.y * HEIGHT + y;
    }

    public double toWorldY(double y) {
        return this.y * HEIGHT + y;
    }

    public int toWorldZ(int z) {
        return this.z * LENGTH + z;
    }

    public double toWorldZ(double z) {
        return this.z * LENGTH + z;
    }
    //</editor-fold>

<<<<<<< HEAD
    /**
     * Gets a block from chunk coordinates.
     */
    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }

    /**
     * Fills the blocks array with empty blocks value.
     */
=======
    public boolean isLoaded() {
        return world.isLoaded(this);
    }

    public void load() {
        if (!isLoaded()) {
            world.loadChunk(this);
            world.getChunkGenerator().generate(this, x, y, z);
        }
    }

    public void unload() {
        if (isLoaded())
            world.unloadChunk(this);
    }

    public Block getBlock(int x, int y, int z) {
        return blocks[x][y][z];
    }

    public Chunk getRelative(int offsetX, int offsetY, int offsetZ) {
        return world.getChunk(
                x + offsetX,
                y + offsetY,
                z + offsetZ
        );
    }

    public Chunk getRelative(Relative relative) {
        return getRelative(
                relative.offsetX,
                relative.offsetY,
                relative.offsetZ
        );
    }

>>>>>>> c2da1efc78c07a3b3c8d57021cb54cdb5cb8f026
    public void fillEmpty() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                for (int z = 0; z < LENGTH; z++)
                    blocks[x][y][z] = new Block(this, x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chunk) {
            Chunk chunk = (Chunk) obj;
            return chunk.world.equals(world) &&
                    chunk.x == x &&
                    chunk.y == y &&
                    chunk.z == z;
        }
        return false;
    }
}