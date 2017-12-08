package xyz.upperlevel.openverse.inventory;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.network.inventory.SlotChangePacket;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseInventory implements Inventory {
    @Getter
    private long id = -1;
    /*
     * Why an List and not a Set you may ask?
     * The first reason is that the inventory listener are designed to be accessed in a fast way
     * and they could be written more slowly: it's much harder to a player/machine to attach/detach from an inventory
     * that it is for it to change
     */
    private List<ChangeListener> listeners = new ArrayList<>();

    @Override
    public void onSlotChange(Slot slot) {
        for (ChangeListener listener : listeners) {
            listener.onChange(this, slot);
        }
    }

    @Override
    public void assignId(long id) {
        if (this.id != -1) {
            throw new IllegalStateException("Id already assigned");
        }
        this.id = id;
    }

    @Override
    public boolean addListener(ChangeListener listener) {
        return listeners.add(listener);
    }

    @Override
    public boolean hasListener(ChangeListener listener) {
        return listeners.contains(listener);
    }

    @Override
    public boolean removeListenr(ChangeListener listener) {
        return listeners.remove(listener);
    }
}
