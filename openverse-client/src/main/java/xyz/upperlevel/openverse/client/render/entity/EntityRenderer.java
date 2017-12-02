package xyz.upperlevel.openverse.client.render.entity;

import lombok.Getter;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.EntityType;

/**
 * This class takes care to render the referred type.
 */
public abstract class EntityRenderer<E extends Entity> {
    @Getter
    protected final EntityType type;

    public EntityRenderer(EntityType type) {
        this.type = type;
    }

    public abstract void render(E entity);

    public abstract void destroy();
}
