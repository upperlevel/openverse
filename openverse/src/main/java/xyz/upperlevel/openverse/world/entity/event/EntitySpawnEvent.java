package xyz.upperlevel.openverse.world.entity.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.world.entity.Entity;

/**
 * Called when an entity spawns.
 */
@Getter
@RequiredArgsConstructor
public class EntitySpawnEvent implements Event {
    private final Entity entity;
}
