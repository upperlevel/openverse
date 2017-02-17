package xyz.upperlevel.opencraft.server.network;

import xyz.upperlevel.utils.packets.encoder.PacketEncoder;
import xyz.upperlevel.utils.packets.packet.Packet;
import xyz.upperlevel.utils.packets.packet.impl.PlayerRotatePacket;

import java.nio.ByteBuffer;

public class PlayerRotatePacketEncoder extends PacketEncoder<PlayerRotatePacket> {

    @Override
    public ByteBuffer encode(PlayerRotatePacket packet) {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES * 2);

        buffer.putFloat(packet.getYaw());
        buffer.putFloat(packet.getPitch());

        return buffer;
    }

    @Override
    public PlayerRotatePacket decoder(ByteBuffer data) {
        if (data.remaining() != Float.BYTES * 2)
            throw new IllegalArgumentException("data corrupted");
        return new PlayerRotatePacket(
                data.getFloat(),
                data.getFloat()
        );
    }
}
