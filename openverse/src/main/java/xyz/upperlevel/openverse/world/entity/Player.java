package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.resource.entity.EntityType;
import xyz.upperlevel.openverse.world.Location;

@Getter
public class Player extends Entity {
    public static final EntityType TYPE = null;

    private final String name;
    private final Connection connection;

    public Player(Location location, String name, Connection connection) {
        super(TYPE, location);
        this.name = name;
        this.connection = connection;
    }


    public EntityType getType() {
        return TYPE;
    }
}
