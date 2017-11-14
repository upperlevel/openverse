package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import lombok.*;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.item.ItemStack;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlotChangePacket implements Packet {
    private long inventoryId;
    private int slotId;
    private ItemStack newItem;


    @Override
    public void toData(ByteBuf out) {
        out.writeLong(inventoryId);
        out.writeInt(slotId);
        newItem.toData(out);
    }

    @Override
    public void fromData(ByteBuf in) {
        inventoryId = in.readLong();
        slotId = in.readInt();
        newItem = ItemStack.fromData(in);
    }
}
