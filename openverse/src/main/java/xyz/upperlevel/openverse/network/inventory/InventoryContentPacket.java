package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.hermes.exceptions.IllegalPacketException;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryContentPacket implements Packet {
    private long inventoryId;
    private ItemStack[] contents;

    public InventoryContentPacket(Inventory inventory) {
        this.inventoryId = inventory.getId();
        contents = new ItemStack[inventory.getSize()];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = inventory.get(i).getContent();
        }
    }

    @Override
    public void toData(ByteBuf out) {
        out.writeLong(inventoryId);
        for (ItemStack stack : contents) {
            stack.toData(out);
        }
    }

    @Override
    public void fromData(ByteBuf in) {
        inventoryId = in.readLong();
        List<ItemStack> content = new ArrayList<>();
        while (in.isReadable()) {
            content.add(ItemStack.fromData(in));
        }
        contents = content.toArray(new ItemStack[0]);
    }

    public void apply(PlayerInventory inventory) {
        if (contents.length > inventory.getSize()) {
            throw new IllegalPacketException(this, "Invalid InventoryContentPacket, packet contents: " + contents.length + "inventory: " + inventory.getSize());
        } else if (contents.length < inventory.getSize()) {
            Openverse.getLogger().warning("Received bad InventoryContents packet, size:" + contents.length + ", actual:" + inventory.getSize());
        }
        for (int i = 0; i < contents.length; i++) {
            inventory.get(i).swap(contents[i]);
        }
    }
}
