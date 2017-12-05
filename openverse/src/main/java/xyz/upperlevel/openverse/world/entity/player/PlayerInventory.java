package xyz.upperlevel.openverse.world.entity.player;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.inventory.*;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeHandSlotPacket;
import xyz.upperlevel.openverse.util.math.MathUtil;

import java.util.Arrays;
import java.util.Iterator;

/**
 * The player's inventory
 * <br>Note: the id is always 0 because the id is only used to identify the modified inventory in the communications,
 * <br>The server can know which player to refer using the received connection while the client already knows it's his inventory
 * <br>So we can assign the same id at every player's inventory
 */
public class PlayerInventory extends InventoryBase {
    private InventoryContent content = new SimpleInventoryContent(4*9);
    private Slot[] slots = new Slot[4*9];
    @Getter
    private int handSlot = 0;

    public PlayerInventory() {
        assignId(0);// See pre-class note
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new Slot(this, i);
            slots[i].swap(content.get(i));
        }
    }

    @Override
    public void onSlotChange(Slot slot) {
        content.set(slot.getId(), slot.getContent());
        super.onSlotChange(slot);
    }

    public Slot get(int index) {
        return slots[index];
    }

    @Override
    public int getSize() {
        return slots.length;
    }

    public ItemStack getHotbarItem(int i) {
        return get(convertHotbarSlot(i)).getContent();
    }

    public Slot getHotbar(int i) {
        return get(convertHotbarSlot(i));
    }

    public ItemStack setHotbarItem(int slot, ItemStack item) {
        return get(convertHotbarSlot(slot)).swap(item);
    }

    public Slot getHand() {
        return getHotbar(handSlot);
    }

    public ItemStack getHandItem() {
        return getHand().getContent();
    }

    public ItemStack setHandItem(ItemStack item) {
        return getHand().swap(item);
    }

    public void unsafeSetHandSlot(int slot) {
        checkHotbarBounds(slot);
        this.handSlot = slot;
    }

    public void setHandSlot(int slot) {
        checkHotbarBounds(slot);
        PlayerChangeHandSlotPacket packet = new PlayerChangeHandSlotPacket(slot);
        Openverse.endpoint().getConnections().forEach(c -> c.send(Openverse.getChannel(), packet));
        this.handSlot = slot;
    }

    public void scrollHand(int direction) {
        direction = MathUtil.clamp(direction, -1, 1);
        // Circular scroll from 0 to max: (a + max) % max
        this.handSlot = (handSlot + direction + 9) % 9;
    }

    public int convertHotbarSlot(int slot) {
        checkHotbarBounds(slot);
        return slot + 3*9;
    }

    protected void checkHotbarBounds(int slot) {
        if (slot < 0 || slot >= 9) {
            throw new IndexOutOfBoundsException("Hotbar index out of range:" + slot);
        }
    }

    @Override
    public Iterator<Slot> iterator() {
        return Arrays.asList((Slot[])slots).iterator();
    }
}
