package xyz.upperlevel.openverse.server.world.chunk.util;

import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.world.chunk.ChunkMap;

/**
 * This class is used to interact with the {@link ChunkMap}.
 * It is used by the (todo) chunk map test class
 */
public interface ChunkMapCallback {
    void onChunkPillarAdd(ServerPlayer player, int x, int z);

    void onChunkPillarRemove(ServerPlayer player, int x, int z);

    void onChunkAdd(ServerPlayer player, int x, int y, int z);

    void onChunkRemove(ServerPlayer player, int x, int y, int z);
}
