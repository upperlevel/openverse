package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.network.SendUniversePacket;
import xyz.upperlevel.openverse.network.SendUniversePacketConverter;

public final class OpenverseProtocol {

    private static Protocol protocol = Protocol.builder()
            // todo adds packets
            .register(SendUniversePacket.class, new SendUniversePacketConverter())
            .build();

    private OpenverseProtocol() {
    }

    public static Protocol get() {
        return protocol;
    }
}
