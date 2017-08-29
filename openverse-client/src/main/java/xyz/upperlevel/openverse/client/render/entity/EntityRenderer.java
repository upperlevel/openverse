package xyz.upperlevel.openverse.client.render.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.openverse.resource.entity.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.event.EntityMoveEvent;

/**
 * This class takes care to render the referred entity.
 */
@Getter
@Setter
public class EntityRenderer {
    private final int id;
    private final EntityType type;

    private Location location;

    public EntityRenderer(Entity entity) {
        this.id = entity.getId();
        this.type = entity.getType();
    }

    public void render() {
        // todo
    }

    public void destroy() {
        // todo
    }
}
