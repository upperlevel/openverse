package xyz.upperlevel.openverse.server.inventory;

import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements PacketListener {
    private long nextId = 0;
    private Map<Long, Inventory> handles = new HashMap<>();

    public InventoryManager() {
        Openverse.channel().register(this);
    }

    public long register(Inventory inventory) {
        inventory.assignId(nextId);
        handles.put(nextId, inventory);
        return nextId++;
    }

    public Inventory get(long id) {
        return handles.get(id);
    }
}
