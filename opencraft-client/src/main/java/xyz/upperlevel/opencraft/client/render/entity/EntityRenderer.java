package xyz.upperlevel.opencraft.client.render.entity;

import xyz.upperlevel.opencraft.client.world.entity.Entity;
import xyz.upperlevel.opencraft.client.world.entity.EntityType;

public interface EntityRenderer<T extends EntityType> {

    T getType();

    void draw(Entity<T> entity);
}