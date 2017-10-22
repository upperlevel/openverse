package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.block.state.BlockStateRegistry;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStateStorage;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.openverse.world.chunk.storage.SimpleBlockStorage;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

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
        return blockStorage.getBlockState(x, y, z).getBlockType();
    }

    public void setBlockType(int x, int y, int z, BlockType blockType) {
        blockStorage.setBlockState(x, y, z, blockType == null ? AIR_STATE : blockType.getDefaultBlockState());
    }


    public BlockState getBlockState(int x, int y, int z) {
        return blockStorage.getBlockState(x, y, z);
    }

    public void setBlockState(int x, int y, int z, BlockState blockState) {
        blockStorage.setBlockState(x, y, z, blockState);
        int light = blockState.getBlockType().getEmittedBlockLight(blockState);
        if (light > 0) {
            setBlockLight(x, y, z, light);
            world.diffuseBlockLight(x + getX() * 16, y + this.y * 16, z + getZ() * 16);
        }
    }


    public int getBlockLight(int x, int y, int z) {
        return blockStorage.getBlockLight(x, y, z);
    }

    public void setBlockLight(int x, int y, int z, int blockLight) {
        blockStorage.setBlockLight(x, y, z, blockLight);
    }
}