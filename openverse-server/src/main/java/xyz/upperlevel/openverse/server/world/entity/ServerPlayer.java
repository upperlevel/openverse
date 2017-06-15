package xyz.upperlevel.openverse.server.world.entity;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Player;

public class ServerPlayer extends ServerEntity implements Player {

    private static final EntityType TYPE = new EntityType("player");

    @Getter
    private final String name;

    @Getter
    private final Connection connection;

    public ServerPlayer(String name, Connection connection, Location location) {
        super(TYPE, location);
        this.name = name;
        this.connection = connection;
    }
}
