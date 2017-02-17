package xyz.upperlevel.opencraft.common.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.common.block.BlockType;
import xyz.upperlevel.opencraft.common.block.BlockState;

import java.util.Objects;

public class Chunk {

    public static final int WIDTH = 16, HEIGHT = 16, LENGTH = 16, SIZE = WIDTH * HEIGHT * LENGTH;

    @Getter
    private World world;

    @Getter
    private int x, y, z;

    @Getter
    private BlockState[][][] blockStates = new BlockState[WIDTH][HEIGHT][LENGTH];

    public Chunk(World world, int x, int y, int z) {
        Objects.requireNonNull(world, "world");
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
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

    public final int getSize() {
        return SIZE;
    }

    public void generate() {
        world.getChunkGenerator().generate(this);
    }

    public BlockType getBlockType(int x, int y, int z) {
        return blockStates[x][y][z] != null ? blockStates[x][y][z].getType() : BlockType.NULL;
    }

    public Chunk setBlockType(BlockType blockType, int x, int y, int z) {
        blockStates[x][y][z] = blockType.generateState();
        return this;
    }

    public BlockState getBlockState(int x, int y, int z) {
        return blockStates[x][y][z] != null ? blockStates[x][y][z] : BlockState.NULL;
    }

    public Chunk setBlockState(BlockState blockState, int x, int y, int z) {
        blockStates[x][y][z] = blockState;
        return this;
    }

    public Block getBlock(int x, int y, int z) {
        return new Block(this, x, y, z);
    }
}
