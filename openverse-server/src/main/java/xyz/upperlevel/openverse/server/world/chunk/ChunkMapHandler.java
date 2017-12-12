package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.util.Aabb2d;
import xyz.upperlevel.openverse.util.math.Aabb2f;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

/**
 * This class is an util with functions that helps on chunk map managing.
 * The {@link ServerPlayer} specified in each can be set also to {@code null}.
 */
@Getter
public abstract class ChunkMapHandler {
    private int radius;

    public ChunkMapHandler(int radius) {
        this.radius = radius;
    }

    public abstract void onChunkPillarAdd(ServerPlayer player, int x, int z);

    public abstract void onChunkPillarRemove(ServerPlayer player, int x, int z);

    public abstract void onChunkAdd(ServerPlayer player, int x, int y, int z);

    public abstract void onChunkRemove(ServerPlayer player, int x, int y, int z);

    public void setRadius(ServerPlayer player, ChunkLocation central, int radius) {
        // TODO!
        this.radius = radius;
    }

    public void addChunkView(ServerPlayer player, ChunkLocation central) {
        for (int x = central.x - radius; x <= central.x + radius; x++) {
            for (int z = central.z - radius; z <= central.z + radius; z++) {
                onChunkPillarAdd(player, x, z);
                for (int y = central.y - radius; y <= central.y + radius; y++) {
                    onChunkAdd(player, x, y, z);
                }
            }
        }
    }

    public void removeChunkView(ServerPlayer player, ChunkLocation central) {
        for (int x = central.x - radius; x <= central.x + radius; x++) {
            for (int z = central.z - radius; z <= central.z + radius; z++) {
                for (int y = central.y - radius; y <= central.y + radius; y++) {
                    onChunkRemove(player, x, y, z);
                }
                onChunkPillarRemove(player, x, z);
            }
        }
    }

    public void moveChunkView(ServerPlayer player, ChunkLocation from, ChunkLocation to) {
        Aabb2d fromChkView = new Aabb2d(from.x - radius, from.z - radius, from.x + radius, from.z + radius);
        Aabb2d newChkView = new Aabb2d(to.x - radius, to.z - radius, to.x + radius, to.z + radius);

        boolean apart = !fromChkView.isColliding(newChkView);
        for (int xOff = -radius; xOff <= radius; xOff++) {
            for (int zOff = -radius; zOff <= radius; zOff++) {
                int fromChkX = from.x + xOff;
                int fromChkZ = from.z + zOff;
                int newChkX = to.x + xOff;
                int newChkZ = to.z + zOff;
                // If the old pillar is out from new
                if (apart || !newChkView.testPoint(fromChkX, fromChkZ)) {
                    for (int fromChkY = from.y - radius; fromChkY <= from.y + radius; fromChkY++) {
                        onChunkRemove(player, fromChkX, fromChkY, fromChkZ);
                    }
                    onChunkPillarRemove(player, fromChkX, fromChkZ);
                }
                // If the new pillar is out from old
                if (apart || !fromChkView.testPoint(newChkX, newChkZ)) {
                    onChunkPillarAdd(player, newChkX, newChkZ);
                    for (int newChkY = to.y - radius; newChkY <= to.y + radius; newChkY++) {
                        onChunkAdd(player, newChkX, newChkY, newChkZ);
                    }
                }
                // If the new pillar is in the old view
                else {
                    for (int yOff = -radius; yOff <= radius; yOff++) {
                        int fromChkY = from.y + yOff;
                        int newChkY = to.y + yOff;

                        // If new y is out from old view
                        if (newChkY < (from.y - radius) || newChkY > (from.y + radius)) {
                            onChunkAdd(player, newChkX, newChkY, newChkZ);
                        }

                        // If old y is out from new one
                        if (fromChkY < (to.y - radius) || fromChkY > (to.y + radius)) {
                            onChunkRemove(player, fromChkX, fromChkY, fromChkZ);
                        }
                    }
                }
            }
        }
    }
}
