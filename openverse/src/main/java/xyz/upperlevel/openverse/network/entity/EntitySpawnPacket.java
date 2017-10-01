package xyz.upperlevel.openverse.network.entity;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.entity.EntityType;

/**
 * This packet is sent from the server to the client to spawn an entity.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntitySpawnPacket implements Packet {
    private int entityId;
    private EntityType entityType;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(entityId);
       // writeString(entityType.getId(), out);
    }

    @Override
    public void fromData(ByteBuf in) {
        entityId = in.readInt();
        //entityType = new EntityType(readString(in));
    }
}
