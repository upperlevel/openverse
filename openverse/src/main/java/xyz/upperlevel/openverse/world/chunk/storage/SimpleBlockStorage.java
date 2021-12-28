package xyz.upperlevel.openverse.world.chunk.storage;

import lombok.Getter;
import xyz.upperlevel.openverse.OpenverseProxy;
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

public class SimpleBlockStorage implements BlockStorage {
    @Getter
    private final OpenverseProxy module;

    @Getter
    private final World world;

    @Getter
    private final Chunk chunk;

    @Getter
    private BlockStateStorage blockStateStorage;

    @Getter
    private Map<Integer, BlockEntity> blockEntityMap;

    @Getter
    private NibbleArray blockSkylightArray;

    @Getter
    private NibbleArray blockLightArray;

    public SimpleBlockStorage(OpenverseProxy module, Chunk chunk) {
        this.module = module;

        this.world = chunk.getWorld();
        this.chunk = chunk;
        this.blockStateStorage = createBlockStateStorage();
        this.blockEntityMap = new HashMap<>();
        this.blockSkylightArray = new NibbleArray(16 * 16 * 16);
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
    public BlockState setBlockState(int x, int y, int z, BlockState blockState) {
        return blockStateStorage.setBlockState(x, y, z, blockState != null ? blockState : AIR_STATE);
    }


    protected BlockStateStorage createBlockStateStorage() {
        return new SimpleBlockStateStorage(module);
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
    public int setBlockLight(int x, int y, int z, int blockLight) {
        final int index = x << 8 | y << 4 | z;
        int old = blockLightArray.get(index);
        blockLightArray.set(index, (byte) blockLight);
        return old;
    }


    @Override
    public int getBlockSkylight(int x, int y, int z) {
        return blockSkylightArray.get(x << 8 | y << 4 | z);
    }

    @Override
    public int setBlockSkylight(int x, int y, int z, int blockSkyLight) {
        final int index = x << 8 | y << 4 | z;
        int old = blockSkylightArray.get(index);
        blockSkylightArray.set(index, (byte) blockSkyLight);
        return old;
    }
}
