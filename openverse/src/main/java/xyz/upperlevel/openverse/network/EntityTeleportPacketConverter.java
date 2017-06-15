package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;

public class EntityTeleportPacketConverter implements PacketConverter<EntityTeleportPacket> {
    @Override
    public byte[] toData(EntityTeleportPacket packet) {
        ByteBuffer out = ByteBuffer.allocate(
                        Long.BYTES +                            //id
                        packet.getWorldName().length() + 1 +    //world name
                        Double.BYTES * 3 +                      //x, y, z
                        Double.BYTES + 2                        //yaw, pitch
        );
        out.putLong(packet.getId());

        SerialUtil.writeString(packet.getWorldName(), out);

        out.putDouble(packet.getX());
        out.putDouble(packet.getY());
        out.putDouble(packet.getZ());

        out.putDouble(packet.getYaw());
        out.putDouble(packet.getPitch());

        return out.array();
    }

    @Override
    public EntityTeleportPacket toPacket(byte[] bytes) {
        ByteBuffer in = ByteBuffer.wrap(bytes);
        return new EntityTeleportPacket(
                in.getLong(),
                SerialUtil.readString(in),
                in.getDouble(), in.getDouble(), in.getDouble(),
                in.getDouble(), in.getDouble()
        );
    }
}
