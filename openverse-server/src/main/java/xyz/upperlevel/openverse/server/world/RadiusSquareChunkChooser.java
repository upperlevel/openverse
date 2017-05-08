package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.network.SendChunkPacket;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.Player;

import static xyz.upperlevel.openverse.Openverse.getChannel;

public class RadiusSquareChunkChooser extends PlayerChunkMap {

    @Getter
    private int radius, side;

    public RadiusSquareChunkChooser(ServerWorld handle, int radius) {
        super(handle);
        setRadius(radius);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        side = radius * 2 + 1;
    }

    @Override
    public void onChunkChange(ChunkLocation from, @NonNull ChunkLocation to, @NonNull Player player) {
        Box oldChunkArea = from != null ? new Box(
                from.x - radius,
                from.y - radius,
                from.z - radius,
                side,
                side,
                side
        ) : new Box();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    int cx = to.x + x;
                    int cy = to.y + y;
                    int cz = to.z + z;

                    // if the chunk isn't inside the old chunk area
                    if (!oldChunkArea.isIn(new Box(cx, cy, cz, 1, 1, 1)))
                        player.getConnection()
                                .send(getChannel(), new SendChunkPacket(
                                        getHandle().getChunk(x, y, z))
                                );
                }
            }
        }
    }
}
