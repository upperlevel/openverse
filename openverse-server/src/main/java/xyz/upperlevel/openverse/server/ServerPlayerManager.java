package xyz.upperlevel.openverse.server;

import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.PlayerManager;
import xyz.upperlevel.openverse.server.world.entity.ServerPlayer;

public class ServerPlayerManager extends PlayerManager<ServerPlayer> implements Listener {

    public ServerPlayerManager(OpenverseProxy proxy) {
        super(proxy);
    }

    @Override
    public ServerPlayer createPlayer(Connection connection) {
        return new ServerPlayer("hello", connection, null);
    }
}
