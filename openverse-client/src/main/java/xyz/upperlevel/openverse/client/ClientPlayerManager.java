package xyz.upperlevel.openverse.client;

import lombok.NonNull;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.PlayerManager;
import xyz.upperlevel.openverse.client.world.entity.ClientPlayer;

public class ClientPlayerManager extends PlayerManager<ClientPlayer> {

    public ClientPlayerManager(@NonNull OpenverseClient handle) {
        super(handle);
    }

    @Override
    public ClientPlayer createPlayer(Connection connection) {
        return new ClientPlayer("hello", connection);
    }
}
