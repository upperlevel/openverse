package xyz.upperlevel.openverse.world.entity.player.events;

import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.world.entity.player.Player;

public class PlayerInventoryCloseEvent extends PlayerInventoryEvent {
    public PlayerInventoryCloseEvent(Player player, Inventory inventory) {
        super(player, inventory);
    }
}
