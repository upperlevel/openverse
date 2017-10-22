package xyz.upperlevel.openverse.world.chunk.storage;

import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.chunk.Block;
import xyz.upperlevel.openverse.world.block.blockentity.BlockEntity;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.storage.utils.NibbleArray;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SimpleBlockStorage implements BlockStorage {
    private final World world;
    private final Chunk chunk;

    private BlockStateStorage blockStateStorage;
    private Map<Integer, BlockEntity> blockEntityMap;
    private NibbleArray blockSkyLightArray;
    private NibbleArray blockLightArray;

    public SimpleBlockStorage(Chunk chunk) {
        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.blockStateStorage = createBlockStateStorage();
        this.blockEntityMap = new HashMap<>();
        this.blockSkyLightArray = new NibbleArray(16 * 16 * 16);
        this.blockLightArray = new NibbleArray(16 * 16 * 16);
    }


    @Override
    public Block getBlock(int x, int y, int z) {
        return new Block(getChunk(), x, y, z, this);
    }


    @Override
    public BlockState getBlockState(int x, int y, int z) {
        return blockStateStorage.getBlockState(x, y, z);
    }

    @Override
    public void setBlockState(int x, int y, int z, BlockState blockState) {
        blockStateStorage.setBlockState(x, y, z, blockState != null ? blockState : AIR_STATE);
    }


    protected BlockStateStorage createBlockStateStorage() {
        return new SimpleBlockStateStorage();
    }


    @Override
    public BlockEntity getBlockEntity(int x, int y, int z) {
        return blockEntityMap.get(x << 8 | y << 4 | z);
    }

    @Override
    public void setBlockEntity(int x, int y, int z, BlockEntity blockEntity) {
        blockEntityMap.put(x << 8 | y << 4 | z, blockEntity);
    }

    public Collection<BlockEntity> getBlockEntities() {
        return blockEntityMap.values();
    }


    @Override
    public int getBlockLight(int x, int y, int z) {
        return blockLightArray.get(x << 8 | y << 4 | z);
    }

    @Override
    public void setBlockLight(int x, int y, int z, int blockLight) {
        blockLightArray.set(x << 8 | y << 4 | z, (byte) blockLight);
    }


    @Override
    public int getBlockSkyLight(int x, int y, int z) {
        return blockSkyLightArray.get(x << 8 | y << 4 | z);
    }

    @Override
    public void setBlockSkyLight(int x, int y, int z, int blockSkyLight) {
        blockSkyLightArray.set(x << 8 | y << 4 | z, (byte) blockSkyLight);
    }
}
