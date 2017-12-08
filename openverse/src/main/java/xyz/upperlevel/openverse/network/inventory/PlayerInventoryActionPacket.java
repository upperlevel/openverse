package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.inventory.PlayerInventorySession.InteractAction;


/**
 * Packet for sending {@link xyz.upperlevel.openverse.world.entity.player.Player} interaction with the inventory!
 * <br>The packet pretty much explains himself.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerInventoryActionPacket implements Packet {
    private InteractAction action;
    private int slotId;

    @Override
    public void toData(ByteBuf out) {
        out.writeByte(action.toId());
        out.writeInt(slotId);
    }

    @Override
    public void fromData(ByteBuf in) {
        action = InteractAction.fromId(in.readByte());
        slotId = in.readInt();
    }
}
