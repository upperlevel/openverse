package xyz.upperlevel.openverse.world.entity.player.events;

import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.world.entity.player.Player;

public class PlayerInventoryOpenEvent extends PlayerInventoryEvent {
    public PlayerInventoryOpenEvent(Player player, Inventory inventory) {
        super(player, inventory);
    }
}
