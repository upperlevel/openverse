package xyz.upperlevel.openverse.world.event;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.event.CancellableEvent;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.player.Player;

public class WorldChangeEvent extends CancellableEvent {
    @Getter
    private final World oldWorld;
    @Getter
    @Setter
    private World world;
    @Getter
    private final Player player;

    public WorldChangeEvent(Player player, World world) {
        this.world = world;
        this.player = player;
        this.oldWorld = player.getWorld();
    }
}
