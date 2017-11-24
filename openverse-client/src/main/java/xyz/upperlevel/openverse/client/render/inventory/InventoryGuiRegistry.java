package xyz.upperlevel.openverse.client.render.inventory;

import xyz.upperlevel.openverse.client.render.inventory.player.PlayerInventoryGui;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class InventoryGuiRegistry {
    private Map<Class<? extends Inventory>, Function<? extends Inventory, InventoryGui<?>>> inventories = new HashMap<>();

    public InventoryGuiRegistry() {
        registerDefaults();
    }

    protected void registerDefaults() {
        register(PlayerInventory.class, PlayerInventoryGui::new);
    }

    @SuppressWarnings("unchecked")
    public <T extends Inventory> void register(Class<T> type, Function<T, InventoryGui<T>> renderer) {
        if (inventories.putIfAbsent(type, (Function)renderer) != null) {
            throw new IllegalStateException("InventoryGui creator already registered for type: " + type);
        }
    }

    public <T extends Inventory> boolean override(Class<T> type, Function<T, InventoryGui<?>> renderer) {
        return inventories.put(type, renderer) != null;
    }

    public boolean unregister(Class<Inventory> type) {
        return inventories.remove(type) != null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Inventory> Function<T, InventoryGui<?>> get(Class<T> type) {
        return (Function<T, InventoryGui<?>>) inventories.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T extends Inventory> InventoryGui<T> create(T inv) {
        Function<Inventory, InventoryGui<T>> creator = (Function) get(inv.getClass());
        if (creator == null) return null;
        return creator.apply(inv);
    }
}
