package xyz.upperlevel.openverse.inventory;

import xyz.upperlevel.openverse.item.ItemStack;

import java.util.Iterator;

/**
 * A class that contains the {@link Inventory} internal states, usually only the {@link Slot}s are used to change the internal InventoryContents
 * <br>this class won't contain any of the Inventory watchers nor any change listeners
 */
public interface InventoryContent extends Iterable<ItemStack> {
    /**
     * Returns the capacity of the inventory (how many slots it has)
     * @return the inventory's capacity
     */
    int getCapacity();

    /**
     * Returns true only when all of the stored {@link ItemStack}s are empty, false otherwise
     * @return true whenever the inventory is empty, false otherwise
     */
    default boolean isEmpty() {
        final int capacity = getCapacity();
        for (int i = 0; i < capacity; i++) {
            if (!get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the ItemStack in the specified index or {@link ItemStack#EMPTY} if it's empty
     * @param index the Inventory index
     * @return the {@link ItemStack} at that index
     * @exception IndexOutOfBoundsException if the index is outside of the inventory range
     */
    ItemStack get(int index);

    /**
     * Changes the ItemStack in the specified index and returns the old one (or {@link ItemStack#EMPTY} if empty
     * @param index the Inventory index
     * @param item the new item
     * @return the old {@link ItemStack} at that index
     * @exception IndexOutOfBoundsException if the index is outside of the inventory range
     */
    ItemStack set(int index, ItemStack item);

    /**
     * Clears the inventory setting every contained {@link ItemStack} to empty
     */
    default void clear() {
        if (isEmpty()) {
            return;
        }
        for (int i = 0; i < getCapacity(); i++) {
            set(i, ItemStack.EMPTY);
        }
    }

    @Override
    default Iterator<ItemStack> iterator() {
        return new Iterator<ItemStack>() {
            private int index = -1;

            @Override
            public boolean hasNext() {
                return index < getCapacity();
            }

            @Override
            public ItemStack next() {
                return get(++index);
            }

            @Override
            public void remove() {
                if (index < 0) {
                    throw new IllegalStateException("Called iterator.remove() before iterator.next()");
                }
                set(index, ItemStack.EMPTY);
            }
        };
    }
}
