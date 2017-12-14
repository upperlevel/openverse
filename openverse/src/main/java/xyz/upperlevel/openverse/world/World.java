package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector3i;
import xyz.upperlevel.openverse.util.math.Aabb3d;
import xyz.upperlevel.openverse.util.math.LineVisitor3d;
import xyz.upperlevel.openverse.util.math.bfs.FastFloodAlgorithm;
import xyz.upperlevel.openverse.util.math.bfs.FastFloodContext;
import xyz.upperlevel.openverse.world.block.state.BlockState;
import xyz.upperlevel.openverse.world.chunk.*;
import xyz.upperlevel.openverse.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import static xyz.upperlevel.openverse.util.math.MathUtil.ceili;
import static xyz.upperlevel.openverse.util.math.MathUtil.floori;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

@Getter
@Setter
public class World {
    private final String name;
    private ChunkPillarProvider chunkPillarProvider;

    // These instances are used to diffuse lights
    // There is one of them foreach light type todo :(
    private FastFloodAlgorithm lightDiffusion;
    private FastFloodAlgorithm skylightDiffusion;

    // A value that atm goes from 0 to 1 where 0 is the deep night and 1 the day
    private float skylight = 1f; // todo update skylight value

    public World(String name) {
        this.name = name;
        this.chunkPillarProvider = new SimpleChunkPillarProvider(this);

        lightDiffusion = new FastFloodAlgorithm();
        skylightDiffusion = new FastFloodAlgorithm();
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

    public boolean isChunkLoaded(int x, int y, int z) {
        return chunkPillarProvider.hasChunk(x, y, z);
    }

    public boolean isBlockLoaded(int x, int y, int z) {
        return chunkPillarProvider.hasChunk(x >> 4, y, z >> 4);
    }

    /**
     * Gets the {@link Chunk} at the given chunk coordinates.
     */
    public Chunk getChunk(int x, int y, int z) {
        ChunkPillar plr = getChunkPillar(x, z);
        if (plr == null) {
            return null;
        }
        return plr.getChunk(y);
    }

    public Chunk getChunkFromBlock(int blockX, int blockY, int blockZ) {
        return getChunk(blockX >> 4, blockY >> 4, blockZ >> 4);
    }

    public void setChunk(Chunk chunk) {
        ChunkPillar plr = getChunkPillar(chunk.getX(), chunk.getZ());
        if (plr != null) {
            plr.setChunk(chunk.getY(), chunk);
        }
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
        return getChunkFromBlock(x, y, z).getBlock(x & 0xF, y & 0xF, z & 0xF);
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

    /**
     * A context for the block light fast flood algorithm.
     * Needed to use the FastFlood API.
     */
    private final FastFloodContext blockLightContext = new FastFloodContext() {
        @Override
        public boolean isOutOfBounds(int x, int y, int z) {
            return false;
        }

        @Override
        public int getValue(int x, int y, int z) {
            return getBlockLight(x, y, z);
        }

        @Override
        public void setValue(int x, int y, int z, int value) {
            Chunk chk = getChunkFromBlock(x, y, z);
            if (chk != null) {
                // Sets the block light directly to the storage without diffusion.
                // This is the only way to access block light storage.
                chk.getBlockStorage().setBlockLight(x & 0xF, y & 0xF, z & 0xF, value);
            }
        }
    };

    /**
     * Gets the block light at the given location.
     *
     * @param x the x-axis location
     * @param y the y-axis location
     * @param z the z-axis location
     * @return the block light
     */
    public int getBlockLight(int x, int y, int z) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        return chunk == null ? 0 : chunk.getBlockLight(x & 0xF, y & 0xF, z & 0xF);
    }

    /**
     * Appends a new node to the light diffusion algorithm.
     * To effectively apply the lights use {@link #updateBlockLights()}.
     *
     * @param x          the x-axis location
     * @param y          the y-axis location
     * @param z          the z-axis location
     * @param blockLight the block light
     */
    public void appendBlockLight(int x, int y, int z, int blockLight, boolean instantUpdate) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk != null) {
            int oldLev = getBlockLight(x, y, z);
            if (oldLev > blockLight) {
                lightDiffusion.addRemovalNode(x, y, z, oldLev);
                lightDiffusion.addNode(x, y, z, blockLight);
            } else if (oldLev < blockLight) {
                lightDiffusion.addNode(x, y, z, blockLight);
            }
            if (instantUpdate)
                updateBlockLights();
        }
    }

    /**
     * Diffuses all the block lights appended until now.
     */
    public void updateBlockLights() {
        lightDiffusion.start(blockLightContext);
    }

    /**
     * A context for the block skylight fast flood algorithm.
     * Needed to use the FastFlood API.
     */
    private final FastFloodContext blockSkylightContext = new FastFloodContext() {
        @Override
        public boolean isOutOfBounds(int x, int y, int z) {
            return false;
        }

        @Override
        public int getValue(int x, int y, int z) {
            return getBlockSkylight(x, y, z);
        }

        @Override
        public void setValue(int x, int y, int z, int value) {
            Chunk chk = getChunkFromBlock(x, y, z);
            if (chk != null) {
                // Sets the skylight directly to the storage without diffusion.
                // This is the only way to access block skylight storage.
                chk.getBlockStorage().setBlockSkylight(x & 0xF, y & 0xF, z & 0xF, value);
            }
        }
    };

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
        return chunk == null ? 0 : chunk.getBlockSkylight(x & 0xF, y & 0xF, z & 0xF);
    }

    /**
     * Appends a new node to the skylight diffusion algorithm.
     * To effectively apply the skylights use {@link #updateBlockSkylights()}.
     *
     * @param x             the x-axis location
     * @param y             the y-axis location
     * @param z             the z-axis location
     * @param blockSkylight the block light
     */
    public void appendBlockSkylight(int x, int y, int z, int blockSkylight, boolean instantUpdate) {
        Chunk chunk = getChunkFromBlock(x, y, z);
        if (chunk != null) {
            int oldLev = getBlockLight(x, y, z);
            if (oldLev > blockSkylight) {
                skylightDiffusion.addRemovalNode(x, y, z, oldLev);
                skylightDiffusion.addNode(x, y, z, blockSkylight);
            } else if (oldLev < blockSkylight) {
                skylightDiffusion.addNode(x, y, z, blockSkylight);
            }
            if (instantUpdate)
                updateBlockLights();
        }
    }

    /**
     * Diffuses all the block skylights appended until now.
     */
    public void updateBlockSkylights() {
        skylightDiffusion.start(blockSkylightContext);
    }
}
