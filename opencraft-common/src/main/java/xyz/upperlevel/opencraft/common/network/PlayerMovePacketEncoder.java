package xyz.upperlevel.opencraft.common.network;

import xyz.upperlevel.opencraft.common.network.packet.PlayerMovePacket;
import xyz.upperlevel.utils.packets.encoder.PacketEncoder;
import xyz.upperlevel.utils.packets.packet.Packet;
import xyz.upperlevel.utils.packets.packet.impl.PlayerMovePacket;

import java.nio.ByteBuffer;

public class PlayerMovePacketEncoder extends PacketEncoder<PlayerMovePacket> {

    @Override
    public ByteBuffer encode(PlayerMovePacket packet) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES * 3);

        buffer.putDouble(packet.getX());
        buffer.putDouble(packet.getY());
        buffer.putDouble(packet.getZ());

        return buffer;
    }

    @Override
    public PlayerMovePacket decoder(ByteBuffer data) {
        if (data.remaining() != Double.BYTES * 3)
            throw new IllegalArgumentException("data corrupted");
        return new PlayerMovePacket(
                data.getDouble(),
                data.getDouble(),
                data.getDouble()
        );
    }
}
