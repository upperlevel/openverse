package xyz.upperlevel.openverse.inventory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.item.ItemStack;

/**
 * A class that is used to interface an Inventory slot
 * <br>The id is used to help the {@link Inventory} identify the slots
 * <br>TODO: in the future releases only slots-slots will be permitted (expect special cases) to help server check hacking
 */
@RequiredArgsConstructor
public class Slot {
    @Getter
    private final Inventory parent;
    @Getter
    private final int id;
    private ItemStack content = ItemStack.EMPTY;

    public ItemStack getContent() {
        return content;
    }

    public ItemStack take() {
        ItemStack taken = content;
        content = ItemStack.EMPTY;
        parent.onSlotChange(this);
        return taken;
    }

    public void swap(Slot other) {
        ItemStack taken = content;
        content = other.content;
        other.content = taken;
        parent.onSlotChange(this);
        other.parent.onSlotChange(other);
    }

    public ItemStack swap(ItemStack other) {
        ItemStack taken = content;
        content = other;
        parent.onSlotChange(this);
        return taken;
    }

    public ItemStack add(ItemStack other) {
        if (content.isEmpty()) {
            content = other;
            parent.onSlotChange(this);
            return ItemStack.EMPTY;
        } else if (content.getType() == other.getType()){
            int remaining = content.addCount(other.getCount());
            other.setCount(remaining);
            parent.onSlotChange(this);
            return other;
        } else {
            return other;
        }
    }
}
