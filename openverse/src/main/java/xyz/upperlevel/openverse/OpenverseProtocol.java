package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.network.*;
import xyz.upperlevel.openverse.network.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;

import static xyz.upperlevel.hermes.PacketSide.*;

public final class OpenverseProtocol {

    private static Protocol protocol = Protocol.builder()
            .enableSubChannels()//Register sub-channel related packets
            .packet(SHARED, EntityTeleportPacket.class)
            .packet(CLIENT, ChunkCreatePacket.class)
            .build();

    private OpenverseProtocol() {
    }

    public static Protocol get() {
        return protocol;
    }
}
