package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;


/**
 * Packet for sending {@link xyz.upperlevel.openverse.world.entity.player.Player} interaction with the inventory!
 * <br>The packet pretty much explains himself.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerInventoryActionPacket implements Packet {
    private Action action;
    private int slotId;

    @Override
    public void toData(ByteBuf out) {
        out.writeByte(action.toId());
        out.writeInt(slotId);
    }

    @Override
    public void fromData(ByteBuf in) {
        action = Action.fromId(in.readByte());
        slotId = in.readInt();
    }

    public enum Action {
        RIGHT_CLICK,
        LEFT_CLICK,
        SHIFT_RIGHT_CLICK,
        SHIFT_LEFT_CLICK,
        DROP,
        DROP_STACK;

        private static Action[] values = values();

        public byte toId() {
            return (byte) ordinal();
        }

        public static Action fromId(byte id) {
            return values[id];
        }
    }
}
