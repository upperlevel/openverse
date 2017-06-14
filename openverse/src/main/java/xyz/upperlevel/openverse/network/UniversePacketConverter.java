package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static xyz.upperlevel.openverse.network.SerialUtil.getSize;
import static xyz.upperlevel.openverse.network.SerialUtil.readString;
import static xyz.upperlevel.openverse.network.SerialUtil.writeString;

public class UniversePacketConverter implements PacketConverter<UniversePacket> {

    @Override
    public byte[] toData(UniversePacket packet) {
        List<String> worlds = packet.getWorlds();

        ByteBuffer out = ByteBuffer.allocate(getSize(worlds));

        // worlds
        for (String world : worlds)
            writeString(world, out);

        return out.array();
    }

    @Override
    public UniversePacket toPacket(byte[] data) {
        ByteBuffer in = ByteBuffer.wrap(data);

        // worlds
        List<String> worlds = new ArrayList<>();
        while (in.hasRemaining())
            worlds.add(readString(in));

        return new UniversePacket(worlds);
    }
}
