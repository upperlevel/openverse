package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class UniverseInfoPacketConverter implements PacketConverter<UniverseInfoPacket> {

    // gets world names total byte size
    private int getSize(List<String> wn) {
        int i = 0;
        for (String n : wn)
            i += n.length() * Character.BYTES;
        return i;
    }

    @Override
    public byte[] toData(UniverseInfoPacket packet) {
        List<String> wn = packet.getWorldNames();

        ByteBuffer out = ByteBuffer.allocate(getSize(wn));
        for (String n : wn) {
            out.putInt(n.length());
            for (char c : n.toCharArray())
                out.putChar(c);
        }
        // todo out.flip();

        return out.array();
    }

    @Override
    public UniverseInfoPacket toPacket(byte[] data) {
        List<String> wn = new ArrayList<>();

        ByteBuffer in = ByteBuffer.wrap(data);
        while (in.hasRemaining()) {
            // initializes a new string
            StringBuilder n = new StringBuilder();
            int sz = in.getInt(); // the size of the building string

            for (int i = 0; i < sz; i++)
                n.append(in.getChar(i));

            wn.add(n.toString());
        }
        return new UniverseInfoPacket(wn);
    }
}
