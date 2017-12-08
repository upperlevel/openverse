package xyz.upperlevel.openverse.inventory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Collections;
import java.util.Iterator;

@RequiredArgsConstructor
public class PlayerInventorySession {
    @Getter
    private final Player player;
    @Getter
    private final Inventory inventory;
    @Getter
    private HandInventory hand = new HandInventory();

    public void onInteract(Slot slot, InteractAction action) {
        inventory.onPlayerInteract(player, hand.get(0), slot, action);
    }

    public void onClose() {

    }

    public static class HandInventory extends BaseInventory {
        private Slot slot = new Slot(this, 0);

        @Override
        public Slot get(int slotId) {
            return slotId == 0 ? slot : null;
        }

        public Slot get() {
            return slot;
        }

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public Iterator<Slot> iterator() {
            return Collections.singleton(slot).iterator();
        }
    }

    public enum InteractAction {
        RIGHT_CLICK,
        LEFT_CLICK,
        SHIFT_RIGHT_CLICK,
        SHIFT_LEFT_CLICK,
        DROP,
        DROP_STACK;

        private static InteractAction[] values = values();

        public byte toId() {
            return (byte) ordinal();
        }

        public static InteractAction fromId(byte id) {
            return values[id];
        }
    }
}

