package xyz.upperlevel.openverse.world.entity.event;

import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Player;

public class PlayerMoveEvent extends EntityMoveEvent {
    public PlayerMoveEvent(Player player, Location newLocation) {
        super(player, newLocation);
    }

    public Player getPlayer() {
        return (Player) getEntity();
    }
}
