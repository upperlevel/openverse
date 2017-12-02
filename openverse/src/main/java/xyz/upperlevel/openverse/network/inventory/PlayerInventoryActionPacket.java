package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlayerInventoryActionPacket implements Packet {
    private long inventoryId;
    private int slotId;
    private Action action;

    @Override
    public void toData(ByteBuf out) {
        out.writeLong(inventoryId);
        out.writeInt(slotId);
        out.writeShort(action.toId());
    }

    @Override
    public void fromData(ByteBuf in) {
        inventoryId = in.readLong();
        slotId = in.readInt();
        action = Action.fromId(in.readByte());
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
