package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class GetChunkPacketConverter implements PacketConverter<GetChunkPacket> {

    @Override
    public byte[] toData(GetChunkPacket packet) {
        String world = packet.getWorld();
        return ByteBuffer.allocate(world.length() + Integer.BYTES * 3)
                .put(world.getBytes(StandardCharsets.UTF_8))
                .putInt(packet.x)
                .putInt(packet.y)
                .putInt(packet.z)
                .array();
    }

    @Override
    public GetChunkPacket toPacket(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        byte[] world = new byte[data.length - Integer.BYTES * 3];
        buffer.get(world);
        return new GetChunkPacket(
                new String(world, StandardCharsets.UTF_8),
                buffer.getInt(),
                buffer.getInt(),
                buffer.getInt()
        );
    }
}
