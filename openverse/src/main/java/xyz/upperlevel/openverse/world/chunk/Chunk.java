package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.event.BlockUpdateEvent;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.state.BlockState;
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
        return getBlockState(x, y, z).getBlockType();
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     * <br>Note: the change doesn't get sent to the other side (server/client) a packet should be sent that will cause the state change
     * <br>Example: if the player breaks a block don't send a BlockChange packet but a BlockBreak
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @param type the new type the block will be set to (precisely his default state)
     * @param blockUpdate if true calls a block update
     * @return the old state (the block was before this call)
     */
    public BlockState setBlockType(int x, int y, int z, BlockType type, boolean blockUpdate) {
        return setBlockState(x, y, z, type != null ? type.getDefaultBlockState() : AIR_STATE, blockUpdate);
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     * <br>Note: the change doesn't get sent to the other side (server/client) a packet should be sent that will cause the state change
     * <br>Example: if the player breaks a block don't send a BlockChange packet but a BlockBreak
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @param type the new type the block will be set to (precisely his default state)
     * @return the old state (the block was before this call)
     */
    public BlockState setBlockType(int x, int y, int z, BlockType type) {
        return setBlockState(x, y, z, type != null ? type.getDefaultBlockState() : AIR_STATE, true);
    }

    public BlockState getBlockState(int x, int y, int z) {
        return blockStorage.getBlockState(x, y, z);
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     * <br>Note: the change doesn't get sent to the other side (server/client) a packet should be sent that will cause the state change
     * <br>Example: if the player breaks a block don't send a BlockChange packet but a BlockBreak
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @param blockState the new state the block will be set to
     * @param blockUpdate if true calls a block update
     * @return the old state (the block was before this call)
     */
    public BlockState setBlockState(int x, int y, int z, BlockState blockState, boolean blockUpdate) {
        BlockState oldState = blockStorage.setBlockState(x, y, z, blockState);
        if (blockUpdate) {
            Openverse.getEventManager().call(new BlockUpdateEvent(world, x + (location.x << 4), y + (location.y  << 4), z + (location.z << 4), oldState, blockState));
        }
        return oldState;
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     * <br>Note: the change doesn't get sent to the other side (server/client) a packet should be sent that will cause the state change
     * <br>Example: if the player breaks a block don't send a BlockChange packet but a BlockBreak
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @param blockState the new state the block will be set to
     * @return the old state (the block was before this call)
     */
    public BlockState setBlockState(int x, int y, int z, BlockState blockState) {
        return setBlockState(x, y, z, blockState, true);
    }
}