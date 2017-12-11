package xyz.upperlevel.openverse.server.world.chunk.util;

import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.util.math.Aabb2f;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

/**
 * This class is an util with functions that helps on chunk map managing.
 * The {@link ServerPlayer} specified in each can be set also to {@code null}.
 */
public class ChunkMapHandler {
    private ChunkMapHandler() {
    }

    public static void setRadius(ServerPlayer player, ChunkLocation central, int fromRadius, int toRadius, ChunkMapCallback callback) {
        // Todo: with chunk pillars!
    }

    public static void addChunkView(ServerPlayer player, ChunkLocation central, int radius, ChunkMapCallback callback) {
        for (int x = central.x - radius; x <= central.x + radius; x++) {
            for (int z = central.z - radius; z <= central.z + radius; z++) {
                callback.onChunkPillarAdd(player, x, z);
                for (int y = central.y - radius; y <= central.y + radius; y++) {
                    callback.onChunkAdd(player, x, y, z);
                }
            }
        }
    }

    public static void removeChunkView(ServerPlayer player, ChunkLocation central, int radius, ChunkMapCallback callback) {
        for (int x = central.x - radius; x <= central.x + radius; x++) {
            for (int z = central.z - radius; z <= central.z + radius; z++) {
                for (int y = central.y - radius; y <= central.y + radius; y++) {
                    callback.onChunkRemove(player, x, y, z);
                }
                callback.onChunkPillarRemove(player, x, z);
            }
        }
    }

    public static void moveChunkView(ServerPlayer player, ChunkLocation from, ChunkLocation to, int radius, ChunkMapCallback callback) {
        Aabb2f fromChkView = new Aabb2f(from.x - radius, from.z - radius, from.x + radius, from.z + radius);
        Aabb2f newChkView = new Aabb2f(to.x - radius, to.z - radius, to.x + radius, to.z + radius);

        for (int xOff = -radius; xOff <= radius; xOff++) {
            for (int zOff = -radius; zOff <= radius; zOff++) {
                int fromChkX = from.x + xOff;
                int fromChkZ = from.z + zOff;
                int newChkX = to.x + xOff;
                int newChkZ = to.z + zOff;
                if (!newChkView.inside(fromChkX, fromChkZ)) {
                    for (int fromChkY = from.y - radius; fromChkY <= from.y + radius; fromChkY++) {
                        callback.onChunkRemove(player, fromChkX, fromChkY, fromChkZ);
                    }
                }
                if (!fromChkView.inside(newChkX, newChkZ)) {
                    callback.onChunkPillarAdd(player, newChkX, newChkZ);

                    for (int newChkY = to.y - radius; newChkY <= to.y + radius; newChkY++) {
                        callback.onChunkAdd(player, newChkX, newChkY, newChkZ);
                    }
                } else {
                    for (int yOff = -radius; yOff <= radius; yOff++) {
                        int fromChkY = from.y + yOff;
                        int newChkY = to.y + yOff;

                        if (newChkY < (from.y - radius) || newChkY > (from.y + radius)) {
                            callback.onChunkAdd(player, newChkX, newChkY, newChkZ);
                        }

                        if (fromChkY < (to.y - radius) || fromChkY > (to.y + radius)) {
                            callback.onChunkAdd(player, fromChkX, fromChkY, fromChkZ);
                        }
                    }
                }
            }
        }
    }
}
