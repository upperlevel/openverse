package xyz.upperlevel.openverse.client.world.entity;

import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.entity.BaseEntity;

public abstract class ClientEntity extends BaseEntity {

    public ClientEntity(EntityType type) {
        super(type);
    }

    public abstract void render();
}
