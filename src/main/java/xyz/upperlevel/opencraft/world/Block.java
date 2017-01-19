package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.block.BlockState;

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
        setState(id.createState());
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

    @Override
    public boolean equals(Object object) {
        return object instanceof Block ? loc.equals(this.getLoc()) : super.equals(object);
    }
}