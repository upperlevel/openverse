package xyz.upperlevel.openverse.network.world.entity;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerChangeHandSlotPacket implements Packet {
    @Getter
    @Setter
    private int newHandSlotId;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(newHandSlotId);
    }

    @Override
    public void fromData(ByteBuf in) {
        newHandSlotId = in.readInt();
    }
}
