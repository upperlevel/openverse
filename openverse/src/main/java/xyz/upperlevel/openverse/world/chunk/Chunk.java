package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.event.BlockUpdateEvent;
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

    private boolean skylightColumnsUpdated[];

    public Chunk(ChunkPillar chunkPillar, int y) {
        this.world = chunkPillar.getWorld();
        this.chunkPillar = chunkPillar;
        this.y = y;
        this.location = new ChunkLocation(getX(), y, getZ());
        this.blockStorage = new SimpleBlockStorage(this);

        skylightColumnsUpdated = new boolean[16 * 16];
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

        int wx = getX() << 4 | x;
        int wy = getY() << 4 | y;
        int wz = getZ() << 4 | z;

        // Block Light
        int oldEmLight = oldState.getBlockType().getEmittedBlockLight(oldState);
        int emLight = blockState.getBlockType().getEmittedBlockLight(blockState);

        if (oldEmLight > 0) {
            world.removeBlockLight(wx, wy, wz, oldEmLight, blockUpdate);
        }
        if (emLight > 0) {
            world.appendBlockLight(wx, wy, wz, emLight, blockUpdate);
        }

        if (blockUpdate) {
            rebuildHeightFor(x, z, y, true);
            updateSkylightOnBlockChange(x, z, y);

            Openverse.getEventManager().call(new BlockUpdateEvent(world, x + (location.x << 4), y + (location.y << 4), z + (location.z << 4), oldState, blockState));
        }
        return oldState;
    }

    public void reloadAllLights() {

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

    public boolean isHeightmapTestable() {
        return y > chunkPillar.getHeightmapMinimum() >> 4 && y < chunkPillar.getHeightmapMaximum() >> 4;
    }

    public void updateSkylightGaps() {
        if (!isHeightmapTestable()) {
            return;
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                if (!skylightColumnsUpdated[x * 16 + z]) {
                    skylightColumnsUpdated[x * 16 + z] = true;

                    int currX = getX() << 4 | x;
                    int currZ = getZ() << 4 | z;
                    int height = chunkPillar.getHeight(x, z);

                    if (height >> 4 == y) {
                        for (BlockFace face : new BlockFace[]{BlockFace.BACK, BlockFace.FRONT, BlockFace.LEFT, BlockFace.RIGHT}) {
                            int relX = currX + face.offsetX;
                            int relZ = currZ + face.offsetZ;
                            int relHeight = world.getHeight(relX, relZ);

                            int from = Math.max(height, relHeight) - 1;
                            if (from >> 4 == y) {
                                from &= 0xF;
                            } else {
                                from = 15;
                            }
                            int to = Math.min(height, relHeight);
                            if (to >> 4 == y) {
                                to &= 0xF;
                            } else {
                                to = 0;
                            }
                            for (int y = from; y >= to; y--) {
                                world.appendBlockSkylight(currX, this.y << 4 | y, currZ, 15, false);
                            }
                        }
                    }
                }
            }
        }
        world.updateBlockSkylights();
    }

    public void updateDirectSkylight() {
        if (y < chunkPillar.getHeightmapMinimum() >> 4) {
            return;
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int ch = chunkPillar.getHeight(x, z);
                if (y > ch >> 4) {
                    for (int y = 15; y >= 0; y--) {
                        blockStorage.setBlockSkylight(x, y, z, 15);
                    }
                } else if (y == ch >> 4) {
                    for (int y = 15; y >= (ch & 0xF); y--) {
                        blockStorage.setBlockSkylight(x, y, z, 15);
                    }
                }
            }
        }
    }

    protected void updateSkylightRemoveLight(int x, int z) {
        for (int y = 15; y >= 0; y--) {
            if (blockStorage.getBlockState(x, y, z) != AIR_STATE) return;
            blockStorage.setBlockSkylight(x, y, z, 0);
        }
        // No block found, need to update the chunk below
        Chunk chunkBelow = chunkPillar.getChunk(y - 1);
        if (chunkBelow != null) {
            chunkBelow.updateSkylightRemoveLight(x, z);
        }
    }

    public void updateSkylightOnBlockChange(int x, int z, int initY) {
        final int height = chunkPillar.getHeight(x, z);
        final int realY = y * 16;
        if (height < realY + initY) { // Block broken
            int startY = height < realY ? 0 : (height & 15) + 1;
            for (int y = startY; y <= initY; y++) {
                blockStorage.setBlockSkylight(x, y, z, 15);
            }
            if (height < realY) {
                // height is below this chunk, update chunk below
                Chunk chunkBelow = chunkPillar.getChunk(this.y - 1);
                if (chunkBelow != null) {
                    chunkBelow.updateSkylightOnBlockChange(x, z, 15);
                }
            }
        } else if (height == realY + initY) {// Block placed directly under sun
            for (int y = initY - 1; y >= 0; y--) {
                if (blockStorage.getBlockState(x, y, z) != AIR_STATE) return;
                blockStorage.setBlockSkylight(x, y, z, 0);
            }
            // No block found, update chunk below
            Chunk chunkBelow = chunkPillar.getChunk(this.y - 1);
            if (chunkBelow != null) {
                chunkBelow.updateSkylightRemoveLight(x, z);
            }
        }// else height > initY ; Block changed under the height, who cares
    }

    public void updateSkylights() {
        updateDirectSkylight();
        //TODO: updateSkylightGaps();
    }

    public void rebuildHeightMap() {
        // Avoid recalculating if every value is over the chunk
        final int realY = y * 16;
        final int maxChunkY = realY + 15;
        if (chunkPillar.getHeightmapMinimum() > maxChunkY) return;

        for (int x = 0; x < 16; x++) {
            xzLoop:
            for (int z = 0; z < 16; z++) {
                int pillarHeight = chunkPillar.getHeight(x, z);
                if (pillarHeight > maxChunkY) continue;
                for (int y = 15; y >= 0; y--) {
                    if (blockStorage.getBlockState(x, y, z) != AIR_STATE) {
                        chunkPillar.setHeight(x, z, realY + y);
                        continue xzLoop;
                    }
                }
                if (pillarHeight >= realY) { // If there was a block in this chunk
                    Chunk chunkBelow = chunkPillar.getChunk(y - 1);
                    if (chunkBelow != null) {
                        chunkBelow.rebuildHeightFor(x, z, 15, false);
                    } else {
                        chunkPillar.setHeight(x, z, realY);
                    }
                }
            }
        }
    }

    public void rebuildHeightFor(int x, int z, int minYToCheck, boolean checkHeightMap) {
        final int realY = y * 16;
        if (checkHeightMap && chunkPillar.getHeight(x, z) > realY + minYToCheck) {
            return;
        }

        for (int y = minYToCheck; y >= 0; y--)  {
            if (blockStorage.getBlockState(x, y, z) != AIR_STATE) {
                chunkPillar.setHeight(x, z, realY + y);
                return;
            }
        }
        // If the code arrived here there's no chunk in the chunk's xz
        // So ask the chunk below if he has any block to update
        Chunk chunkBelow = chunkPillar.getChunk(y - 1);
        if (chunkBelow != null) {
            chunkBelow.rebuildHeightFor(x, z, 15, false);
        } else {
            chunkPillar.setHeight(x, z, realY);
        }
    }
}