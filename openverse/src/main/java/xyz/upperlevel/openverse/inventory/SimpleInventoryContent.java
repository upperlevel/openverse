package xyz.upperlevel.openverse.inventory;

import xyz.upperlevel.openverse.item.ItemStack;

import java.util.Arrays;

public class SimpleInventoryContent implements InventoryContent {
    private ItemStack[] items;

    public SimpleInventoryContent(int size) {
        this.items = new ItemStack[size];
        Arrays.fill(items, ItemStack.EMPTY);
    }

    @Override
    public int getCapacity() {
        return items.length;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack get(int index) {
        return items[index];
    }

    @Override
    public ItemStack set(int index, ItemStack item) {
        ItemStack old = items[index];
        items[index] = item != null ? item : ItemStack.EMPTY;
        return old;
    }
}
