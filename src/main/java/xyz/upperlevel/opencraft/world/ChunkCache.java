package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.opencraft.world.block.state.BlockState;

import static xyz.upperlevel.opencraft.world.Chunk.*;

@RequiredArgsConstructor
public class ChunkCache {

    @Getter
    public final Chunk chunk;

    @Getter
    private final BlockState[][][] blockStates = new BlockState[WIDTH][HEIGHT][LENGTH];

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getLength() {
        return LENGTH;
    }

    public Block getBlock(int x, int y, int z) {
        return new Block(chunk.world, chunk.toWorldX(x), chunk.toWorldY(y), chunk.toWorldZ(z));
    }

    public BlockState getBlockState(int x, int y, int z) {
        return blockStates[x][y][z];
    }

    public void setBlockState(int x, int y, int z, BlockState state) {
        blockStates[x][y][z] = state;
    }
}