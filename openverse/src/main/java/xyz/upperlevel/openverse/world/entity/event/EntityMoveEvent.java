package xyz.upperlevel.openverse.world.entity.event;

import lombok.Getter;
import xyz.upperlevel.event.CancellableEvent;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Entity;

public class EntityMoveEvent extends CancellableEvent {
    @Getter
    private final Entity entity;

    @Getter
    private final Location oldLocation;
    @Getter
    private Location location;

    public EntityMoveEvent(Entity entity, Location newLocation) {
        this.entity = entity;
        oldLocation = entity.getLocation();
        location = newLocation;
    }

    public void setLocation(Location location) {
        if(location.getWorld() != oldLocation.getWorld())
            throw new IllegalArgumentException("Cannot change world");
    }
}
