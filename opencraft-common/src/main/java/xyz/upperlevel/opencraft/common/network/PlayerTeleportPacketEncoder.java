package xyz.upperlevel.opencraft.common.network;

import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.utils.packets.encoder.PacketEncoder;
import xyz.upperlevel.utils.packets.packet.impl.PlayerTeleportPacket;

import java.nio.ByteBuffer;

public class PlayerTeleportPacketEncoder extends PacketEncoder<PlayerTeleportPacket> {

    @Override
    public ByteBuffer encode(PlayerTeleportPacket packet) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES * 3 + Float.BYTES * 2);

        buffer.putDouble(packet.getX());
        buffer.putDouble(packet.getY());
        buffer.putDouble(packet.getZ());
        buffer.putFloat (packet.getYaw());
        buffer.putFloat (packet.getPitch());

        return buffer;
    }

    @Override
    public PlayerTeleportPacket decoder(ByteBuffer data) {
        if (data.remaining() != Double.BYTES * 3 + Float.BYTES * 2)
            throw new IllegalArgumentException("data corrupted");
        return new PlayerTeleportPacket(
                data.getDouble(),
                data.getDouble(),
                data.getDouble(),
                data.getFloat(),
                data.getFloat()
        );
    }
}
