package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.network.*;

import static xyz.upperlevel.hermes.PacketSide.*;

public final class OpenverseProtocol {

    private static Protocol protocol = Protocol.builder()
            .enableSubChannels()//Register sub-channel related packets
            .packet(SHARED, EntityTeleportPacket.class)
            .packet(CLIENT, UniversePacket.class)
            .packet(CLIENT, ChunkCreatePacket.class)
            .packet(CLIENT, ChunkUnloadPacket.class)
            .build();

    private OpenverseProtocol() {
    }

    public static Protocol get() {
        return protocol;
    }
}
