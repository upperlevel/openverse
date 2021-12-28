package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.item.ItemStack;

public class SlotChangePacket implements Packet {
    @Getter
    @Setter
    private long inventoryId;

    @Getter
    @Setter
    private int slotId;

    @Getter
    @Setter
    private ItemStack newItem;

    public SlotChangePacket() {
    }

    public SlotChangePacket(long inventoryId, int slotId, ItemStack newItem) {
        this.inventoryId = inventoryId;
        this.slotId      = slotId;
        this.newItem     = newItem;
    }

    @Override
    public void toData(ByteBuf out) {
        out.writeLong(inventoryId);
        out.writeInt(slotId);

        newItem.toData(out);
    }

    @Override
    public void fromData(ByteBuf in) {
        inventoryId = in.readLong();
        slotId      = in.readInt();
        newItem     = ItemStack.fromData(in);
    }
}
