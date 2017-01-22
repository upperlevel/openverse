package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.world.block.BlockFacePosition;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.block.BlockState;

import java.util.Objects;

public class Block {

    public final World world;
    public final int x, y, z;

    public Block(World world, int x, int y, int z) {
        Objects.requireNonNull(world, "Block world cannot be null");
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Chunk getChunk() {
        return world.getChunk(x, y, z);
    }

    public ChunkCache getChunkCache() {
        return getChunk().cache;
    }

    public BlockId getId() {
        return getState().id;
    }

    public void setId(BlockId id) {
        setState(id.generateState());
    }

    public BlockState getState() {
        return getChunkCache().getBlockState(
                getChunk().toChunkX(x),
                getChunk().toChunkY(y),
                getChunk().toChunkZ(z)
        );
    }

    public void setState(BlockState state) {
        getChunkCache().setBlockState(
                getChunk().toChunkX(x),
                getChunk().toChunkY(y),
                getChunk().toChunkZ(z),
                state
        );
    }

    public Block getRelative(BlockFacePosition position) {
        return getChunkCache().getBlock(
                x + position.directionX,
                y + position.directionY,
                z + position.directionZ
        );
    }

    @Override
    public int hashCode() {
        return 1;
    }
}