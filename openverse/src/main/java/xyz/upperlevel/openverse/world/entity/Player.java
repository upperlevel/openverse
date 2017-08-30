package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.resource.entity.EntityType;
import xyz.upperlevel.openverse.world.Location;

@Getter
public class Player extends Entity {
    public static final EntityType TYPE = null;
    private final String name;

    public Player(String name) {
        super(TYPE);
        this.name = name;
    }

    public EntityType getType() {
        return TYPE;
    }
}
