package xyz.upperlevel.openverse.world.entity;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.resource.EntityType;

public interface Player extends Entity, Nameable {

    EntityType TYPE = new EntityType("player");


    default EntityType getType() {
        return TYPE;
    }

    @Override
    String getName();

    EntityLocation getLocation();

    Connection getConnection();
}
