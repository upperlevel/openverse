package xyz.upperlevel.openverse.network.entity;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent from the server to the client and updates entity movements.
 */
@Getter
@RequiredArgsConstructor
public class EntityTeleportPacket implements Packet {
    private final int entityId;
    private final String worldName;
    private final double x, y, z;
    private final double yaw, pitch;

    @Override
    public void toData(ByteBuf byteBuf) {

    }

    @Override
    public void fromData(ByteBuf byteBuf) {

    }
}
