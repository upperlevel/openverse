package xyz.upperlevel.openverse.network.world.entity;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent from the server to the client and updates entity velocity.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntityChangeVelocityPacket implements Packet {
    private int entityId;
    private double velX, velY, velZ;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(entityId);
        out.writeDouble(velX);
        out.writeDouble(velY);
        out.writeDouble(velZ);
    }

    @Override
    public void fromData(ByteBuf in) {
        entityId = in.readInt();
        velX = in.readDouble();
        velY = in.readDouble();
        velZ = in.readDouble();
    }
}