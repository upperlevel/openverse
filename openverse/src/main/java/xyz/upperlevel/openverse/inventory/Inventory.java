package xyz.upperlevel.openverse.inventory;

import xyz.upperlevel.openverse.inventory.PlayerInventorySession.InteractAction;
import xyz.upperlevel.openverse.world.entity.player.Player;

/**
 * A class that contains a list of {@link Slot}s which are used to modify an inventory
 * <br>The client might not know the full {@link InventoryContent} and it would only use the contained {@link Slot}s to
 * <br>interface himself with the inventory
 */
public interface Inventory extends Iterable<Slot> {
    /**
     * Used by the protocol to assign ids (to make inventories identifiable)
     * @param id the id that will be assigned
     * @throws IllegalStateException if the id is already assigned
     */
    void assignId(long id);

    /**
     * Returns the inventory id (used by the protocol to identify inventories)
     * @return the id or -1 if none has been assigned
     */
    long getId();

    /**
     * Called by {@link Slot} whenever their content is changed (by a Player or by a machine)
     * <br>This will then call all the listeners
     * @param slot the modified slot
     */
    void onSlotChange(Slot slot);

    /**
     * Adds the passed listener to the inventory's listeners
     * @param listener the listener to be added
     * @return true if the listener was added to the list, false otherwise
     */
    boolean addListener(ChangeListener listener);

    /**
     * Checks if the listener is registered within the block's listeners
     * @param listener the listener to be checked
     * @return true if the listener is contained in the block's listeners, false otherwise
     */
    boolean hasListener(ChangeListener listener);

    /**
     * Removes the listener from the block's listeners
     * @param listener the listener to be removed
     * @return true only if the listener was contained in the block's listeners, false otherwise
     */
    boolean removeListenr(ChangeListener listener);

    /**
     * Gets the slot assigned at that slotId,
     * @param slotId the id of the searched Slot
     * @return the {@link Slot} assigned with that id
     * @exception IndexOutOfBoundsException if the slot isn't in the id bounds (no slot with that id is found)
     */
    Slot get(int slotId);

    /**
     * Gets this inventory's slot count
     * @return the number of slots inside the inventory
     */
    int getSize();

    default void onPlayerInteract(Player player, Slot handSlot, Slot slot, InteractAction action) {
        slot.swap(handSlot);
    }

    /**
     * A class that is notified when a {@link Slot} in that inventory is changed
     */
    interface ChangeListener {
        /**
         * Function called whenever a {@link Slot} changes in the listened {@link Inventory},
         * <br>The change could be triggered both by a Player or any automated code/mod/machine...
         * @param inventory the targeted inventory
         * @param slot the {@link Slot} that changed within the targeted {@link Inventory}
         */
        void onChange(Inventory inventory, Slot slot);
    }
}
