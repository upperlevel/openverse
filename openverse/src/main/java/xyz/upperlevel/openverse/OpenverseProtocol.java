package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.network.*;

public final class OpenverseProtocol {

    private static Protocol protocol = Protocol.builder()
            // todo adds packets
            .register(EntityTeleportPacket.class, new EntityTeleportPacketConverter())
            .register(UniversePacket.class, new UniversePacketConverter())
            .register(ChunkCreatePacket.class, new ChunkCreatePacketConverter())
            .register(ChunkUnloadPacket.class, new ChunkUnloadPacketConverter())
            .build();

    private OpenverseProtocol() {
    }

    public static Protocol get() {
        return protocol;
    }
}
