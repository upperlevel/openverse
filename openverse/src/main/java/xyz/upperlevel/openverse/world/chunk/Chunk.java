package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.BlockFace;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.event.BlockLightChangeEvent;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.openverse.world.chunk.storage.SimpleBlockStorage;

import java.util.ArrayList;
import java.util.List;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

@Getter
@Setter
public class Chunk {
    private final World world;
    private final ChunkPillar chunkPillar;
    private final int y;
    private final ChunkLocation location;

    private BlockStorage blockStorage;

    private List<Integer> lights = new ArrayList<>();

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


    public Chunk getRelative(int offsetX, int offsetY, int offsetZ) {
        return world.getChunk(getX() + offsetX, this.y + offsetY, getZ() + offsetZ);
    }

    public Chunk getRelative(BlockFace face) {
        return getRelative(face.offsetX, face.offsetY, face.offsetZ);
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

    /**
     * Sets the given {@link BlockState} at the given coordinates and
     * diffuses its produced light.
     */
    public void setBlockState(int x, int y, int z, BlockState blockState) {
        blockStorage.setBlockState(x, y, z, blockState);
        int light = blockState.getBlockType().getEmittedBlockLight(blockState);
        if (light > 0) {
            setBlockLight(x, y, z, light);
            diffuseBlockLight(x, y, z);
            lights.add(x << 8 | y << 4 | z);
        }
    }

    /**
     * Diffuses the light at the given chunk coordinates.
     */
    public void diffuseBlockLight(int x, int y, int z) {
        world.diffuseBlockLight(x + getX() * 16, y + this.y * 16, z + getZ() * 16);
    }

    /**
     * Diffuses back all the block lights set.
     * Only blocks with a block light higher than 0, obviously.
     */
    public void diffuseAllBlockLights() {
        for (int light : lights) {
            int x = (light & 0xF00) >> 8;
            int y = (light & 0x0F0) >> 4;
            int z = (light & 0x00F);
            diffuseBlockLight(x, y, z);
        }
    }


    public int getBlockLight(int x, int y, int z) {
        return blockStorage.getBlockLight(x, y, z);
    }

    /**
     * Sets the block light without diffusing it.
     */
    public void setBlockLight(int x, int y, int z, int blockLight) {
        Openverse.getEventManager().call(new BlockLightChangeEvent(getBlock(x, y, z), getBlockLight(x, y, z), blockLight));
        blockStorage.setBlockLight(x, y, z, blockLight);
    }
}