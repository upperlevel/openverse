package xyz.upperlevel.openverse.server.world.entity;

import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.BaseEntity;

public class ServerEntity extends BaseEntity {

    public ServerEntity(EntityType type, Location location) {
        super(type, location);
    }
}
