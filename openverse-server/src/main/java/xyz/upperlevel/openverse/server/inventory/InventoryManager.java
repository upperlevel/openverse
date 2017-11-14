package xyz.upperlevel.openverse.server.inventory;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.network.inventory.SlotChangePacket;
import xyz.upperlevel.openverse.server.OpenverseServer;

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

    @PacketHandler
    public void onSlotChange(Connection sender, SlotChangePacket packet) {
        long invId = packet.getInventoryId();
        Inventory inventory;

        if (invId == 0) {
            inventory = OpenverseServer.get().getPlayerManager().getPlayer(sender).getInventory();
        } else {
            inventory = get(invId);
        }
        inventory.onSlotChangePacket(packet.getSlotId(), packet.getNewItem());
    }
}
