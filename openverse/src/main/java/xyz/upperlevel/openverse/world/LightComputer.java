package xyz.upperlevel.openverse.world;

import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.chunk.Block;
import xyz.upperlevel.openverse.world.chunk.Chunk;

/**
 * This is not thread safe!
 * A class that computes the light values for every block
 */
public class LightComputer {
    private final World world;
    /**
     * This is a cache that helps us from creating a new array every time,
     * This will be used to store block position and light using the {@link LightBlockPos} class, refer to its
     * implementation for serialization details.
     */
    private final int[] blocksToUpdate = new int[32768];


    public LightComputer(World world) {
        this.world = world;
    }

    public boolean updateAt(LightType type, int x, int y, int z) {
        if (!world.isBlockLoaded(x, y, z)) {
            return false;
        }
        int lightUpdateCount = 0;

        int computedLight = computeLightAt(type, x, y, z);
        int oldLight = world.getBlock(x, y, z).getLight(type);

        if (computedLight == oldLight) {
            return false;// Nothing to change
        }

        if (computedLight > oldLight) {
            blocksToUpdate[lightUpdateCount++] = LightBlockPos.MIDDLE_NO_LIGHT;
        } else {
            // The new computed light is weaker than the old one,
            // We need to update the blocks around
            int index = 0;
            blocksToUpdate[lightUpdateCount++] = new LightBlockPos(32, 32, 32, oldLight).toInt();

            while (index < lightUpdateCount) {
                LightBlockPos currData = new LightBlockPos(blocksToUpdate[index++]);
                int currX = x + currData.x - 32;
                int currY = y + currData.y - 32;
                int currZ = z + currData.z - 32;
                int currLight = currData.light;
                int currOldLight = world.getBlock(currX, currY, currZ).getLight(type);

                if (currLight != currOldLight) {
                    // The light is different that what we expected in the case that we passed the light to that block
                    // this means that we weren't the ones that gave light to that block, so it's not our problem to
                    // remove the light from it
                    continue; // Jump to the next round
                }

                world.getBlock(currX, currY, currZ).setLight(type, 0);

                if (currLight <= 0) {
                    // No further light to remove
                    continue;
                }

                int dX = Math.abs(currX - x);
                int dY = Math.abs(currY - y);
                int dZ = Math.abs(currZ - z);

                if (dX + dY + dZ > 16) {
                    // The light can only spread 16 blocks from the light source
                    // So, again, this isn't our light
                    continue;
                }

                // See what other blocks it could've influenced
                for (BlockFace side : BlockFace.values()) {
                    int neighbourX = currX + side.offsetX;
                    int neighbourY = currY + side.offsetY;
                    int neighbourZ = currZ + side.offsetZ;
                    if (!world.isBlockLoaded(neighbourX, neighbourY, neighbourZ)) continue;
                    int neighbourLight = world.getBlock(neighbourX, neighbourY, neighbourZ).getLight(type);

                    if (neighbourLight == currLight - 1 && lightUpdateCount < blocksToUpdate.length) {
                        blocksToUpdate[lightUpdateCount++] = new LightBlockPos(
                                neighbourX + 32 - x,
                                neighbourY + 32 - y,
                                neighbourZ + 32 - z,
                                neighbourLight
                        ).toInt();
                    }
                }
            }
        }

        // Now we need to update the blocksToUpdate that we found before

        int index = 0;
        while (index < lightUpdateCount) {
            LightBlockPos currData = new LightBlockPos(blocksToUpdate[index++]);
            int currX = x + currData.x - 32;
            int currY = y + currData.y - 32;
            int currZ = z + currData.z - 32;
            int currOldLight = world.getBlock(currX, currY, currZ).getLight(type);
            int currRealLight = computeLightAt(type, currX, currY, currZ);

            if (currRealLight == currOldLight) {
                // Light already updated
                continue;
            }

            world.getBlock(currX, currY, currZ).setLight(type, currRealLight);

            if (currRealLight < currOldLight) {
                // The computed light is weaker than the old one, we don't need to spread it
                continue;
            }

            int dX = Math.abs(currX - x);
            int dY = Math.abs(currY - y);
            int dZ = Math.abs(currZ - z);

            if (dX + dY + dZ > 16) {
                // The light can only spread 16 blocks from the light source
                // So we can't spread it any further
                continue;
            }

            // Spread the light to the neighbours
            for (BlockFace side : BlockFace.values()) {
                int neighbourX = currX + side.offsetX;
                int neighbourY = currY + side.offsetY;
                int neighbourZ = currZ + side.offsetZ;
                if (!world.isBlockLoaded(neighbourX, neighbourY, neighbourZ)) continue;
                int neighbourLight = world.getBlock(neighbourX, neighbourY, neighbourZ).getLight(type);

                if (neighbourLight < currRealLight && lightUpdateCount < blocksToUpdate.length) {
                    blocksToUpdate[lightUpdateCount++] = new LightBlockPos(
                            neighbourX + 32 - x,
                            neighbourY + 32 - y,
                            neighbourZ + 32 - z,
                            neighbourLight
                    ).toInt();
                }
            }

        }

        return true;
    }

    public int computeLightAt(LightType type, int x, int y, int z) {
        if (type == LightType.SKY && world.canSeeSky(x, y, z)) {
            return 15;
        }
        Block block = world.getBlock(x, y, z);
        if (block == null) return 0;
        int blockLight = type == LightType.SKY ? 0 : block.getType().getEmittedBlockLight(block.getState());
        boolean opaque = block.getType().isOpaque();

        if (opaque) {
            return 0;// Completely opaque blocks cannot receive any other light
        }

        if (blockLight >= 14) {
            // Even if a neighbour has 15 of light he can only cast 15-1 = 14 of light on this block
            return blockLight;
        }

        // Check the neighbours for light sources
        for (BlockFace face : BlockFace.values()) {
            Block rel = block.getRelative(face);
            if (rel == null) continue;
            int lightFromNeighbour = rel.getLight(type) - 1;
            if (lightFromNeighbour > blockLight) {
                blockLight = lightFromNeighbour;
            }
        }
        return blockLight;
    }

    public void spreadNeighbourLight(Chunk chunk) {
        int offX = chunk.getX() * 16;
        int offY = chunk.getY() * 16;
        int offZ = chunk.getZ() * 16;
        for (BlockFace face : BlockFace.values()) {
            if (chunk.getRelative(face) == null) continue;
            // -1-> 0, 0-> 0, 1-> 15
            int sx = face.offsetX == 1 ? 15 : 0;
            // -1-> 0, 0-> 15, 1-> 15
            int ex = face.offsetX == -1 ? 0 : 15;
            // -1-> 0, 0-> 0, 1-> 15
            int sy = face.offsetY == 1 ? 15 : 0;
            // -1-> 0, 0-> 15, 1-> 15
            int ey = face.offsetY == -1 ? 0 : 15;
            // -1-> 0, 0-> 0, 1-> 15
            int sz = face.offsetZ == 1 ? 15 : 0;
            // -1-> 0, 0-> 15, 1-> 15
            int ez = face.offsetZ == -1 ? 0 : 15;

            sx += offX;
            ex += offX;
            sy += offY;
            ey += offY;
            sz += offZ;
            ez += offZ;

            for (int x = sx; x <= ex; x++) {
                for (int y = sy; y <= ey; y++) {
                    for (int z = sz; z <= ez; z++) {
                        updateAt(LightType.BLOCK, x, y, z);
                    }
                }
            }
        }
    }

    private static class LightBlockPos {
        public static final int MIDDLE_NO_LIGHT = new LightBlockPos(32, 32, 32, 0).toInt();
        public final int x, y, z;
        public final int light;

        public LightBlockPos(int x, int y, int z, int light) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.light = light;
        }

        public LightBlockPos(int data) {
            this(
              data & 0b111111,
                    (data >> 6) & 0b111111,
                    (data >> 12) & 0b111111,
                    data >> 18
            );
        }

        public int toInt() {
            // 00000000 00llllzz zzzzyyyy yyxxxxxx
            return x | y << 6 | z << 12 | light << 18;
        }
    }
}
