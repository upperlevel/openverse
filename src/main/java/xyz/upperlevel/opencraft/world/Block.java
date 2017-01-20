package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.block.id.BlockId;
import xyz.upperlevel.opencraft.world.block.state.BlockState;

public class Block {

    @Getter
    public final World world;

    @Getter
    public final Chunk chunk;

    @Getter
    public final int x, y, z;

    public Block(World world, int x, int y, int z) {
        this.world = world;
        this.chunk = world.getChunk(x, y, z);
        this.x     = x;
        this.y     = y;
        this.z     = z;
    }

    private ChunkCache getChunkCache() {
        return chunk.cache;
    }

    public BlockId getId() {
        return getState().id;
    }

    public void setId(BlockId id) {
        setState(id.generateState());
    }

    public BlockState getState() {
        return getChunkCache().getBlockState(
                chunk.toChunkX(x),
                chunk.toChunkY(y),
                chunk.toChunkZ(z)
        );
    }

    public void setState(BlockState state) {
        getChunkCache().setBlockState(
                chunk.toChunkX(x),
                chunk.toChunkY(y),
                chunk.toChunkZ(z),
                state
        );
    }

    @Override
    public int hashCode() {
        return 1;
    }
}