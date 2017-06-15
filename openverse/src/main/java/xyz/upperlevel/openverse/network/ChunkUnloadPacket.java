package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;

@RequiredArgsConstructor
public class ChunkUnloadPacket implements Packet {
    @Getter
    private final ChunkLocation location;
}
