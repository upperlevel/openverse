package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.opencraft.world.block.BlockIds;
import xyz.upperlevel.opencraft.world.block.BlockState;

import static xyz.upperlevel.opencraft.world.Chunk.*;

@RequiredArgsConstructor
public class ChunkCache {

    public final Chunk chunk;

    @Getter
    private final BlockState[][][] blockStates = new BlockState[WIDTH][HEIGHT][LENGTH];

    public final Chunk getChunk() {
        return chunk;
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

    public Block getBlock(int x, int y, int z) {
        return new Block(chunk.world, chunk.toWorldX(x), chunk.toWorldY(y), chunk.toWorldZ(z));
    }

    public BlockState getBlockState(int x, int y, int z) {
        BlockState res = blockStates[x][y][z];
        return res != null ? res : BlockIds.NULL_BLOCK.generateState();
    }

    public void setBlockState(int x, int y, int z, BlockState state) {
        blockStates[x][y][z] = state;
    }
}