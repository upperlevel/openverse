package xyz.upperlevel.openverse.server.world.entity;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.world.entity.Player;

public class ServerPlayer extends BaseEntity implements Player {

    @Getter
    private String name;

    @Getter
    private final Connection connection;

    public ServerPlayer(String name, Connection connection) {
        super(TYPE);
        this.name = name;
        this.connection = connection;
    }
}
