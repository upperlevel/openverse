package xyz.upperlevel.openverse.world.entity.player.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.world.entity.player.Player;

@Getter
@AllArgsConstructor
public class PlayerInventoryEvent implements Event {
    private final Player player;
    private final Inventory inventory;
}
