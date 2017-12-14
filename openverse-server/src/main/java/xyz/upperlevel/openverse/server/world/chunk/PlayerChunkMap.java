package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.util.Aabb2d;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

/**
 * This class is an util with functions that helps on chunk map managing.
 * The {@link ServerPlayer} specified in each can be set also to {@code null}.
 */
@Getter
public abstract class PlayerChunkMap {
    private int radius;

    public PlayerChunkMap(int radius) {
        this.radius = radius;
    }

    public abstract void onChunkPillarSend(ServerPlayer player, int x, int z);

    public abstract void onChunkPillarDestroy(ServerPlayer player, int x, int z);

    public abstract void onChunkSend(ServerPlayer player, int x, int y, int z);

    public abstract void onChunkDestroy(ServerPlayer player, int x, int y, int z);

    public void setRadius(ServerPlayer player, ChunkLocation central, int radius) {
        // TODO!
        this.radius = radius;
    }

    public void sendChunkView(ServerPlayer player, ChunkLocation central) {
        for (int x = central.x - radius; x <= central.x + radius; x++) {
            for (int z = central.z - radius; z <= central.z + radius; z++) {
                onChunkPillarSend(player, x, z);
                for (int y = central.y - radius; y <= central.y + radius; y++) {
                    onChunkSend(player, x, y, z);
                }
            }
        }
    }

    public void destroyChunkView(ServerPlayer player, ChunkLocation central) {
        for (int x = central.x - radius; x <= central.x + radius; x++) {
            for (int z = central.z - radius; z <= central.z + radius; z++) {
                for (int y = central.y - radius; y <= central.y + radius; y++) {
                    onChunkDestroy(player, x, y, z);
                }
                onChunkPillarDestroy(player, x, z);
            }
        }
    }

    public void moveChunkView(ServerPlayer player, ChunkLocation from, ChunkLocation to) {
        Aabb2d fromView = new Aabb2d(
                from.x - radius, from.z - radius,
                from.x + radius, from.z + radius
        );
        Aabb2d toView = new Aabb2d(
                to.x - radius, to.z - radius,
                to.x + radius, to.z + radius
        );
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                // From view update
                int fromX = from.x + x;
                int fromZ = from.z + z;
                if (!toView.testPoint(fromX, fromZ)) {
                    for (int fromY = from.y - radius; fromY <= from.y + radius; fromY++) {
                        onChunkDestroy(player, fromX, fromY, fromZ);
                    }
                    onChunkPillarDestroy(player, fromX, fromZ);
                }
                // To view update
                int toX = to.x + x;
                int toZ = to.z + z;
                if (!fromView.testPoint(toX, toZ)) {
                    onChunkPillarSend(player, toX, toZ);
                    for (int toY = to.y - radius; toY <= to.y + radius; toY++) {
                        onChunkSend(player, toX, toY, toZ);
                    }
                } else {
                    for (int y = -radius; y <= radius; y++) {
                        int fromY = from.y + y;
                        int toY = to.y + y;
                        if (toY < (from.y - radius) || toY > (from.y + radius)) {
                            onChunkSend(player, toX, toY, toZ);
                        }
                        if (fromY < (to.y - radius) || fromY > (to.y + radius)) {
                            onChunkDestroy(player, toX, fromY, toZ);
                        }
                    }
                }
            }
        }
    }
}
