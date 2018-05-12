package xyz.upperlevel.openverse.world.chunk;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.event.BlockUpdateEvent;
import xyz.upperlevel.openverse.world.LightType;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.storage.BlockStorage;
import xyz.upperlevel.openverse.world.chunk.storage.SimpleBlockStorage;

import static xyz.upperlevel.openverse.world.LightType.SKY;
import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;


public class Chunk {
    @Getter
    private final World world;

    @Getter
    private final ChunkPillar chunkPillar;

    @Getter
    private final int y;

    @Getter
    private final ChunkLocation location;

    @Getter
    @Setter
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
            reloadLightForBlock(x, y, z, oldState, blockState);
            //world.recalcSkyLightOpacity(getX() * 16 + x, getY() * 16 + y, getZ() * 16 + z, false);

            // Note thet the updateSkyLight is put after the opacity so that whatever is computed by that will then be
            // replaced by the real light if it is really under the sun
            rebuildHeightFor(x, z, y, true);
            updateSkylightOnBlockChange(x, z, y);

            Openverse.getEventManager().call(new BlockUpdateEvent(world, x + (location.x << 4), y + (location.y << 4), z + (location.z << 4), oldState, blockState));
        }
        return oldState;
    }

    protected void reloadLightForBlock(int x, int y, int z, BlockState oldState, BlockState newState) {
        int realX = getX() * 16 + x;
        int realY = getY() * 16 + y;
        int realZ = getZ() * 16 + z;

        int oldLight = oldState.getBlockType().getEmittedBlockLight(oldState);
        int newLight = newState.getBlockType().getEmittedBlockLight(newState);

        if (oldLight != newLight || oldState.getBlockType().isOpaque() != newState.getBlockType().isOpaque()) {
            world.updateLightAt(realX, realY, realZ);
            world.flushLightChunkUpdates();
        }
    }

    public void reloadAllLightBlocks() {
        for (int x = 0; x < 16; x++) {
            for(int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    BlockState state = blockStorage.getBlockState(x, y, z);
                    if (state.getBlockType().getEmittedBlockLight(state) > 0) {
                        world.updateLightAt(LightType.BLOCK, getX() * 16 + x, getY() * 16 + y, getZ() * 16 + z);
                    }
                }
            }
        }
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
        return y >= chunkPillar.getHeightmapMinimum() >> 4 && y <= chunkPillar.getHeightmapMaximum() >> 4;
    }

    public void updateSkylightGaps() {
        if (!isHeightmapTestable()) return;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int height = chunkPillar.getHeight(x, z);

                // The height is higher than the current chunk, not our problem
                if (y * 16 + 15 <= height) continue;

                int currX = getX() << 4 | x;
                int currZ = getZ() << 4 | z;

                // Retrieve the max neighbour height
                int maxHeight = height;
                for (BlockFace face : new BlockFace[]{BlockFace.BACK, BlockFace.FRONT, BlockFace.LEFT, BlockFace.RIGHT}) {
                    int relX = currX + face.offsetX;
                    int relZ = currZ + face.offsetZ;
                    int relHeight = world.getHeight(relX, relZ);
                    if (relHeight > maxHeight) {
                        maxHeight = relHeight;
                        // And if it's higher than the chunk, exit
                        if (relHeight >= y * 16 + 15) break;
                    }
                }

                if (maxHeight <= height) {
                    // If both are lower than the chunk, then we need to do nothing
                    if (height < y * 16) continue;
                    // If only the relatives are lower than the chunk
                    // but we still have a block under us then we still need to light it
                    maxHeight = height + 1;
                }
                // x == 8 && y == 24 && z == -35
                // Calculate min and max values
                int from = height < y * 16 ? 0 : (height + 1) & 0xF;
                int to   = maxHeight > (y + 1) * 16 ? 15 : maxHeight & 0xF;
                // And finally fill the gaps
                for (int i = from; i <= to; i++) {
                    world.updateLightAt(SKY, currX, y * 16 + i, currZ);
                }
            }
        }
    }

    public void updateDirectSkylight() {
        if (y < chunkPillar.getHeightmapMinimum() >> 4) {
            return;
        }
        int realX = getX() * 16;
        int realY = getY() * 16;
        int realZ = getZ() * 16;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int ch = chunkPillar.getHeight(x, z);
                if (y > ch >> 4) {
                    for (int y = 15; y >= 0; y--) {
                        //world.appendBlockSkylight(realX + x, realY + y, realZ + z, false);
                    }
                } else if (y == ch >> 4) {
                    for (int y = 15; y > (ch & 0xF); y--) {
                        //world.appendBlockSkylight(realX + x, realY + y, realZ + z, false);
                    }
                }
            }
        }
    }

    public void setBlockSkylight(int x, int y, int z, int value) {
        //Openverse.getLogger().info("setBlockSkylight(" + (getX() * 16 + x) + ", " + (getY() * 16 + y) + ", " + (getZ() * 16 + z) + ", " + value + ")");
        if (blockStorage.setBlockSkylight(x, y, z, value) != value) {
            appendToLightUpdatingSet();
            appendNeighborsToLightUpdatingSet(x, y, z);
        }
    }

    public void setBlockLight(int x, int y, int z, int value) {
        if (blockStorage.setBlockLight(x, y, z, value) != value) {
            appendToLightUpdatingSet();
            appendNeighborsToLightUpdatingSet(x, y, z);
        }
    }

    /**
     * Appends this chunk to the light updating set.
     * <br>When the {@link World#flushLightChunkUpdates()} will be called an event (and only one, no matter how many calls) for this chunk will be instanced.
     * <br>This has use in client side where the ChunkRenderer needs to rebuild when light changes
     */
    protected void appendToLightUpdatingSet() {
        world.getLightUpdatingChunks().add(this);
    }

    /**
     * Checks for collision with any neighbor chunk and append any chunk that touches to the light updating set.
     * @param x the block x to be checked
     * @param y the block y to be checked
     * @param z the block z to be checked
     */
    protected void appendNeighborsToLightUpdatingSet(int x, int y, int z) {
        if (x == 0) {
            Chunk c = world.getChunk(getX() - 1, getY(), getZ());
            if (c != null) {
                c.appendToLightUpdatingSet();
            }
        } else if (x == 15) {
            Chunk c = world.getChunk(getX() + 1, getY(), getZ());
            if (c != null) {
                c.appendToLightUpdatingSet();
            }
        }

        if (y == 0) {
            Chunk c = chunkPillar.getChunk(getY() - 1);
            if (c != null) {
                c.appendToLightUpdatingSet();
            }
        } else if (y == 15) {
            Chunk c = chunkPillar.getChunk(getY() + 1);
            if (c != null) {
                c.appendToLightUpdatingSet();
            }
        }

        if (z == 0) {
            Chunk c = world.getChunk(getX(), getY(), getZ() - 1);
            if (c != null) {
                c.appendToLightUpdatingSet();
            }
        } else if (z == 15) {
            Chunk c = world.getChunk(getX(), getY(), getZ() + 1);
            if (c != null) {
                c.appendToLightUpdatingSet();
            }
        }
    }

    /**
     * Removes the skylight in the xz provided until a block is encountered.
     * <br>If no block is found in the entire chunk xz pillar, then the same method in the chunk under this will be called.
     * @param x the x coordinate that will be checked
     * @param z the z coordinate that will be checked
     */
    protected void updateSkylightRemoveLight(int x, int z) {
        final int realX = getX() * 16;
        final int realY = getY() * 16;
        final int realZ = getZ() * 16;
        appendToLightUpdatingSet(); // Append to the updating set no matter what
        for (int y = 15; y >= 0; y--) {
            if (blockStorage.getBlockState(x, y, z) != AIR_STATE) return;
            world.updateLightAt(SKY, realX + x, realY + y,  realZ + z);
        }
        // No block found, need to update the chunk below
        Chunk chunkBelow = chunkPillar.getChunk(y - 1);
        if (chunkBelow != null) {
            chunkBelow.updateSkylightRemoveLight(x, z);
        }
    }

    /**
     * Called whenever a block changes, it looks at the block position and the new skyline height and updates the
     * SkyLight diffusion algorithm accordingly.
     * @param x the x of the changed block
     * @param z the z of the changed block
     * @param initY the y of the changed block
     */
    public void updateSkylightOnBlockChange(int x, int z, int initY) {
        final int height = chunkPillar.getHeight(x, z);
        final int realX = getX() * 16;
        final int realY = y * 16;
        final int realZ = getZ() * 16;
        boolean changed = false;
        //Openverse.getLogger().info("updateSkylightOnBlockChange(" + (realX + x) + ", " + (realY + initY) + ", " + (realZ + z) + ", height=" + height);

        // Remember that height represent the already-updated last block under the sun
        // So, for us to care, it should be below the block (the block got in the way of the sunlight)
        // or it should be the same as the block (the block is placed directly under the sun)
        if (height < realY + initY) { // Block broken
            int startY = height < realY ? 0 : (height & 15) + 1;
            for (int y = startY; y <= initY; y++) {
                world.updateLightAt(SKY, realX + x, realY + y,  realZ + z);
                changed = true;
            }

            if (height < realY) {
                // height is below this chunk, update chunk below
                Chunk chunkBelow = chunkPillar.getChunk(this.y - 1);
                if (chunkBelow != null) {
                    chunkBelow.updateSkylightOnBlockChange(x, z, 15);
                }
            } else if (changed && height == 0) {
                // If the light changed and the last block faces the chunk below
                // Then even the chunk below should be notified
                Chunk c = chunkPillar.getChunk(getY() - 1);
                if (c != null) {
                    c.appendToLightUpdatingSet();
                }
            }
        } else if (height == realY + initY) {// Block placed directly under sun
            world.updateLightAt(SKY, realX + x, realY + initY, realZ + z);// Remove placed block
            for (int y = initY - 1; y >= 0; y--) {
                if (blockStorage.getBlockState(x, y, z) != AIR_STATE) return;
                world.updateLightAt(SKY, realX + x, realY + y, realZ + z);
                changed = true;
            }
            // No block found, update chunk below
            Chunk chunkBelow = chunkPillar.getChunk(this.y - 1);
            if (chunkBelow != null) {
                chunkBelow.updateSkylightRemoveLight(x, z);
            }
        }// else height > initY ; Block changed under the height, who cares
        if (changed) {
            appendToLightUpdatingSet();
        }
    }

    public void updateSkylights() {
        //updateDirectSkylight();
        updateSkylightGaps();
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

    public void updateNearbyChunksLights() {
        world.getLightComputer().spreadNeighbourLight(this);
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

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof Chunk)) return false;
        Chunk o = (Chunk) other;
        return o.world == world && o.location.equals(location);
    }
}