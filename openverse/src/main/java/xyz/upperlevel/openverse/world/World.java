package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3i;
import xyz.upperlevel.event.EventManager;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.util.math.Aabb3d;
import xyz.upperlevel.openverse.util.math.LineVisitor3d;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.*;
import xyz.upperlevel.openverse.world.chunk.event.ChunkLightChangeEvent;
import xyz.upperlevel.openverse.world.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static xyz.upperlevel.openverse.util.math.MathUtil.ceili;
import static xyz.upperlevel.openverse.util.math.MathUtil.floori;
import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

@Getter
@Setter
public class World {
    private final String name;
    private ChunkPillarProvider chunkPillarProvider;

    private LightComputer lightComputer = new LightComputer(this);
    private final Set<Chunk> lightUpdatingChunks = new HashSet<>();

    // A value that atm goes from 0 to 1 where 0 is the deep night and 1 the day
    private float skylight = 0.2f; // todo update skylight value

    public World(String name) {
        this.name = name;
        this.chunkPillarProvider = new SimpleChunkPillarProvider(this);
    }

    public void onTick() {
    }

    public ChunkPillar getChunkPillar(int x, int z) {
        return chunkPillarProvider.getChunkPillar(x, z);
    }

    /**
     * Sets the {@link ChunkPillar} at the given chunk coordinates to the given one.
     */
    public void setChunkPillar(ChunkPillar chunkPillar) {
        chunkPillarProvider.setChunkPillar(chunkPillar);
    }

    public ChunkPillar getChunkPillarFromBlock(int x, int z) {
        return getChunkPillar(x >> 4, z >> 4);
    }

    /**
     * Unloads the {@link ChunkPillar} at the given chunk coordinates (this implies inside chunks unloading).
     */
    public ChunkPillar unloadChunkPillar(int x, int z) {
        return chunkPillarProvider.unloadChunkPillar(x, z);
    }

    public int getHeight(int x, int z) {
        ChunkPillar plr = getChunkPillarFromBlock(x, z);
        return plr == null ? Integer.MAX_VALUE : plr.getHeight(x & 0xF, z & 0xF);
    }

    /**
     * Returns true if the block has a clear path to the sky
     * @param x the block's x coordinate
     * @param y the block's y coordinate
     * @param z the block's z coordinate
     * @return true if the block can see the sky directly
     */
    public boolean canSeeSky(int x, int y, int z) {
        return getHeight(x, z) <= y;
    }

    public boolean isChunkLoaded(int x, int y, int z) {
        return chunkPillarProvider.hasChunk(x, y, z);
    }

    public boolean isBlockLoaded(int x, int y, int z) {
        return chunkPillarProvider.hasChunk(x >> 4, y >> 4, z >> 4);
    }

    public boolean isAreaLoaded(int startX, int startY, int startZ, int endX, int endY, int endZ) {
        int sX = startX >> 4;
        int sY = startY >> 4;
        int sZ = startZ >> 4;
        int eX = endX >> 4;
        int eY = endY >> 4;
        int eZ = endZ >> 4;

        for (int x = sX; x <= eX; x++) {
            for (int y = sY; y <= eY; y++) {
                for(int z = sZ; z <= eZ; z++) {
                    if (!isChunkLoaded(x, y, z)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isAreaLoaded(int x, int y, int z, int radius) {
        return isAreaLoaded(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
    }

    /**
     * Gets the {@link Chunk} at the given chunk coordinates.
     */
    public Chunk getChunk(int x, int y, int z) {
        ChunkPillar plr = getChunkPillar(x, z);
        return plr == null ? null : plr.getChunk(y);
    }

    public Chunk getChunkFromBlock(int blockX, int blockY, int blockZ) {
        return getChunk(blockX >> 4, blockY >> 4, blockZ >> 4);
    }

    public boolean setChunk(Chunk chunk) {
        ChunkPillar plr = getChunkPillar(chunk.getX(), chunk.getZ());
        if (plr == null) {
            return false;
        }
        plr.setChunk(chunk.getY(), chunk);
        return true;
    }

    /**
     * Unloads the {@link Chunk} at the given chunk coordinates.
     */
    public Chunk unloadChunk(int x, int y, int z) {
        return getChunkPillar(x, z).unloadChunk(y);
    }


    /**
     * Gets the {@link Block} at the given world coordinates.
     * It will create a new instance of the {@link Block} object.
     */
    public Block getBlock(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? null : chunk.getBlock(x & 0xF, y & 0xF, z & 0xF);
    }

    public Block getBlock(Vector3i loc) {
        return getBlock(loc.x, loc.y, loc.z);
    }

    public Block getBlock(double x, double y, double z) {
        return getBlock(floori(x), floori(y), floori(z));
    }

    public List<Aabb3d> getBlockCollisions(Entity entity, Aabb3d box) {
        List<Aabb3d> res = new ArrayList<>();

        final int minX = floori(box.minX) - 1;
        final int minY = floori(box.minY) - 1;
        final int minZ = floori(box.minZ) - 1;
        final int maxX = ceili(box.maxX) + 1;
        final int maxY = ceili(box.maxY) + 1;
        final int maxZ = ceili(box.maxZ) + 1;

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                ChunkPillar plr = getChunkPillar(x >> 4, z >> 4);
                if (plr == null) {
                    continue;
                }
                for (int y = minY; y < maxY; y++) {
                    Chunk chk = plr.getChunk(y >> 4);
                    if (chk == null) {
                        continue;
                    }
                    BlockState state = chk.getBlockState(x & 15, y & 15, z & 15);
                    if (state == AIR_STATE) {
                        continue;
                    }
                    state.getBlockType().addCollisionBoxes(state, entity, x, y, z, box, res);
                }
            }
        }
        return res;
    }

    public BlockState getBlockState(int x, int y, int z) {
        Chunk chk = getChunkFromBlock(x, y, z);
        return chk == null ? AIR_STATE : chk.getBlockState(x & 0xF, y & 0xF, z & 0xF);
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
        Chunk chk = getChunkFromBlock(x, y, z);
        if (chk != null) {
            return chk.setBlockState(x & 0xF, y & 0xF, z & 0xF, blockState);
        }
        return null;
    }

    public LineVisitor3d.RayCastResult rayCast(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        return LineVisitor3d.rayCast(startX, startY, startZ, endX, endY, endZ, (x, y, z, f) -> {
            BlockState state = getBlockState(x, y, z);
            // If the block is not loaded, better return
            return state == null || state != AIR_STATE && state.getBlockType().collisionRaytrace(state, this, x, y, z, startX, startY, startZ, endX, endY, endZ);
        });
    }

    public int getBlockLight(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? 0 : chunk.getBlockLight(x & 0xF, y & 0xF, z & 0xF);
    }

    /**
     * Gets the block skylight at the given location.
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @return the block skylight
     */
    public int getBlockSkylight(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk == null) {
            return 0;
        }
        return chunk.getBlockSkylight(x & 0xF, y & 0xF, z & 0xF);
    }

    public void setBlockSkylight(int x, int y, int z, int skylight) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk == null) {
            return;
        }
        chunk.setBlockSkylight(x & 0xF, y & 0xF, z & 0xF, skylight);
    }

    public boolean updateLightAt(LightType type, int x, int y, int z) {
        return lightComputer.updateAt(type, x, y, z);
    }

    public boolean updateLightAt(int x, int y, int z) {
        boolean changed = false;
        changed |= lightComputer.updateAt(LightType.SKY, x, y, z);
        changed |= lightComputer.updateAt(LightType.BLOCK, x, y, z);
        return changed;
    }

    public void flushLightChunkUpdates() {
        EventManager eventManager = Openverse.getEventManager();
        for (Chunk chunk : lightUpdatingChunks) {
            eventManager.call(new ChunkLightChangeEvent(chunk));
        }
        lightUpdatingChunks.clear();
    }
}
