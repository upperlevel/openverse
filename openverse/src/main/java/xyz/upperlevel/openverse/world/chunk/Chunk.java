package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.openverse.world.chunk.storage.SimpleBlockStorage;

@Getter
@Setter
public class Chunk {
    private final World world;
    private final ChunkPillar chunkPillar;
    private final int y;
    private final ChunkLocation location;

    private BlockStorage blockStorage;

    public Chunk(ChunkPillar chunkPillar, int y) {
        this.world = chunkPillar.getWorld();
        this.chunkPillar = chunkPillar;
        this.y = y;
        this.location = new ChunkLocation(getX(), y, getZ());
        this.blockStorage = new SimpleBlockStorage(this);
    }

    /**
     * The {@link Chunk} X is equal to the {@link ChunkPillar} X.
     */
    public int getX() {
        return chunkPillar.getX();
    }

    /**
     * The {@link Chunk} Z is equal to the {@link ChunkPillar} Z.
     */
    public int getZ() {
        return chunkPillar.getZ();
    }

    public Block getBlock(int x, int y, int z) {
        return blockStorage.getBlock(x, y, z);
    }


    public BlockType getBlockType(int x, int y, int z) {
        return blockStorage.getBlockType(x, y, z);
    }

    public void setBlockType(int x, int y, int z, BlockType blockType) {
        blockStorage.setBlockType(x, y, z, blockType);
    }


    public BlockState getBlockState(int x, int y, int z) {
        return blockStorage.getBlockState(x, y, z);
    }

    public void setBlockState(int x, int y, int z, BlockState blockState) {
        blockStorage.setBlockState(x, y, z, blockState);
    }


    public int getBlockLight(int x, int y, int z) {
        return blockStorage.getBlockLight(x, y, z);
    }

    public void setBlockLight(int x, int y, int z, int blockLight) {
        blockStorage.setBlockLight(x, y, z, blockLight);
    }
}