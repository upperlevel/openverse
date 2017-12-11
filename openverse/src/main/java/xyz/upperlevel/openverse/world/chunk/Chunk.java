package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.event.BlockUpdateEvent;
import xyz.upperlevel.openverse.util.math.bfs.FastFloodAlgorithm;
import xyz.upperlevel.openverse.util.math.bfs.FastFloodContext;
import xyz.upperlevel.openverse.world.BlockFace;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockType;
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
        return getBlockState(x, y, z).getBlockType();
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     * <br>Note: the change doesn't get sent to the other side (server/client) a packet should be sent that will cause the state change
     * <br>Example: if the player breaks a block don't send a BlockChange packet but a BlockBreak
     *
     * @param x           the x-axis location
     * @param y           the y-axis location
     * @param z           the z-axis location
     * @param type        the new type the block will be set to (precisely his default state)
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
     * @param x    the x-axis location
     * @param y    the y-axis location
     * @param z    the z-axis location
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
     * @param x           the x-axis location
     * @param y           the y-axis location
     * @param z           the z-axis location
     * @param blockState  the new state the block will be set to
     * @param blockUpdate if true calls a block update
     * @return the old state (the block was before this call)
     */
    public BlockState setBlockState(int x, int y, int z, BlockState blockState, boolean blockUpdate) {
        BlockState oldState = blockStorage.setBlockState(x, y, z, blockState);
        if (blockUpdate) {
            Openverse.getEventManager().call(new BlockUpdateEvent(world, x + (location.x << 4), y + (location.y << 4), z + (location.z << 4), oldState, blockState));
        }
        return oldState;
    }

    /**
     * Sets the {@link BlockState} at the given coordinates to the given one.
     * <br>Note: the change doesn't get sent to the other side (server/client) a packet should be sent that will cause the state change
     * <br>Example: if the player breaks a block don't send a BlockChange packet but a BlockBreak
     *
     * @param x          the x-axis location
     * @param y          the y-axis location
     * @param z          the z-axis location
     * @param blockState the new state the block will be set to
     * @return the old state (the block was before this call)
     */
    public BlockState setBlockState(int x, int y, int z, BlockState blockState) {
        return setBlockState(x, y, z, blockState, true);
    }

    /**
     * Gets the block light at the given chunk location.
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @return the block light
     */
    public int getBlockLight(int x, int y, int z) {
        return blockStorage.getBlockLight(x, y, z);
    }

    /**
     * Gets the block skylight at the given chunk location.
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @return the block skylight
     */
    public int getBlockSkylight(int x, int y, int z) {
        return blockStorage.getBlockSkylight(x, y, z);
    }

    /**
     * Diffuses the skylights for the given chunk and
     * the neighbour chunks. To set and update them it must be called
     * {@link World#updateBlockSkylights()}.
     */
    public void appendBlockSkylights(boolean instantUpdate) {
        int mny = (y - 1) << 4;
        int mxy = (y + 1) << 4 | 0xF;
        int hmv = chunkPillar.getHeightmapMinimum();
        if (hmv >= mny && hmv <= mxy) {
            for (int nx = getX() - 1; nx <= getX() + 1; nx++) {
                for (int nz = getZ() - 1; nz <= getZ() + 1; nz++) {
                    ChunkPillar nb = world.getChunkPillar(nx, nz);
                    for (int i = 0; i < 256; i++) {
                        int y = nb.getHeightmap()[i];
                        if (y >= mny && y <= mxy) {
                            int x = nx << 4 | i >> 4;
                            int z = nz << 4 | i & 0xF;
                            world.appendBlockSkylight(x, y, z, 15, false);
                        }
                    }
                }
            }
        }
        if (instantUpdate) {
            world.updateBlockSkylights();
        }
    }
}