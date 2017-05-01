package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;


@RequiredArgsConstructor
public class GetChunkPacket implements Packet {

    @Getter
    @NonNull
    public final String world;

    @Getter
    @NonNull
    public final int x, y, z;

    public GetChunkPacket(String world, ChunkLocation location) {
        this.world = world;
        x = location.x;
        y = location.y;
        z = location.z;
    }

    public ChunkLocation getLocation() {
        return new ChunkLocation(x, y, z);
    }
}
