package xyz.upperlevel.openverse.server;

import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.event.impl.ConnectionCloseEvent;
import xyz.upperlevel.hermes.event.impl.ConnectionOpenEvent;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {
    public static final String PLAYER_NAME = "Test";

    private final Map<String, Player> playersByName = new HashMap<>();
    private final Map<Connection, Player> playersByConnection = new HashMap<>();

    public PlayerManager() {
        ((Server) Openverse.getEndpoint()).getEventManager().register(this);
    }

    public void register(@NonNull Player player) {
        playersByName.put(player.getName(), player);
        playersByConnection.put(player.getConnection(), player);
    }

    public Player get(String name) {
        return playersByName.get(name);
    }

    public Player get(Connection connection) {
        return playersByConnection.get(connection);
    }

    public Collection<Player> get() {
        return playersByName.values();
    }

    public void unregister(@NonNull Player player) {
        playersByName.remove(player.getName());
        playersByConnection.remove(player.getConnection());
    }

    public void clear() {
        playersByName.clear();
        playersByConnection.clear();
    }

    protected Player createPlayer(Connection connection) {
        return new Player(new Location(Universe.get().getWorlds().iterator().next(), 0, 0, 0).copy(), PLAYER_NAME, connection);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConnect(ConnectionOpenEvent event) {
        register(createPlayer(event.getConnection()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisconnect(ConnectionCloseEvent event) {
        unregister(get(event.getConnection()));
    }
}
