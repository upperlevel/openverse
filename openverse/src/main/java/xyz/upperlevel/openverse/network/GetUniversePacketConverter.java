package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;
import xyz.upperlevel.openverse.world.Universe;

public class GetUniversePacketConverter implements PacketConverter<GetUniversePacket> {

    public GetUniversePacketConverter() {
    }

    @Override
    public byte[] toData(GetUniversePacket packet) {
        return new byte[0];
    }

    @Override
    public GetUniversePacket toPacket(byte[] data) {
        return new GetUniversePacket();
    }
}
