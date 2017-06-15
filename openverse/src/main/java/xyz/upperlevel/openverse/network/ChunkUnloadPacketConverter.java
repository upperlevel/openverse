package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;

import static xyz.upperlevel.openverse.network.SerialUtil.readChunkLocation;
import static xyz.upperlevel.openverse.network.SerialUtil.writeChunkLocation;

public class ChunkUnloadPacketConverter implements PacketConverter<ChunkUnloadPacket> {
    @Override
    public byte[] toData(ChunkUnloadPacket packet) {
        ByteBuffer out = ByteBuffer.allocate(3 * Integer.BYTES);
        writeChunkLocation(packet.getLocation(), out);
        return out.array();
    }

    @Override
    public ChunkUnloadPacket toPacket(byte[] bytes) {
        return new ChunkUnloadPacket(readChunkLocation(ByteBuffer.wrap(bytes)));
    }
}
