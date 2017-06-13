package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.network.UniversePacket;
import xyz.upperlevel.openverse.network.UniversePacketConverter;

public final class OpenverseProtocol {

    private static Protocol protocol = Protocol.builder()
            // todo adds packets
            .register(UniversePacket.class, new UniversePacketConverter())
            .build();

    private OpenverseProtocol() {
    }

    public static Protocol get() {
        return protocol;
    }
}
