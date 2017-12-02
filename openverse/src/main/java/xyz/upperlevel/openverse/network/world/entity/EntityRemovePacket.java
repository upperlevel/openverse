package xyz.upperlevel.openverse.network.world.entity;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent from the server to the client to remove an entity.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntityRemovePacket implements Packet {
    private int entityId;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(entityId);
    }

    @Override
    public void fromData(ByteBuf in) {
        entityId = in.readInt();
    }
}
