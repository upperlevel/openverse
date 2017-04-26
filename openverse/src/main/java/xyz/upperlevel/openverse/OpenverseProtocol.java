package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.network.GetUniversePacket;
import xyz.upperlevel.openverse.network.GetUniversePacketConverter;
import xyz.upperlevel.openverse.network.UniverseInfoPacket;
import xyz.upperlevel.openverse.network.UniverseInfoPacketConverter;

public final class OpenverseProtocol {

    private static Protocol protocol = Protocol.builder()
            // todo adds packets
            .register(GetUniversePacket.class,  new GetUniversePacketConverter())
            .register(UniverseInfoPacket.class, new UniverseInfoPacketConverter())
            .build();

    private OpenverseProtocol() {
    }

    public static Protocol get() {
        return protocol;
    }
}
