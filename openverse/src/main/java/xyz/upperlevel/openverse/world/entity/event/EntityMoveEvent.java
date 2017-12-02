package xyz.upperlevel.openverse.world.entity.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.CancellableEvent;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Entity;

@Getter
@RequiredArgsConstructor
public class EntityMoveEvent extends CancellableEvent {
    private final Entity entity;
    private final Location oldLocation;
    private final Location location;

    public EntityMoveEvent(Entity entity, Location newLocation) {
        this.entity = entity;
        this.oldLocation = entity.getLocation();
        this.location = newLocation;
    }

    public void setLocation(Location location) {
        if (location.getWorld() != oldLocation.getWorld())
            throw new IllegalArgumentException("Cannot change world");
    }
}
