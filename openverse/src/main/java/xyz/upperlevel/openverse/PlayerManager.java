package xyz.upperlevel.openverse;

import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.event.impl.ConnectionCloseEvent;
import xyz.upperlevel.hermes.event.impl.ConnectionOpenEvent;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class PlayerManager<P extends Player> implements Listener {

    private final Map<String, P> playersByName = new HashMap<>();
    private final Map<Connection, P> playersByConnection = new HashMap<>();

    public PlayerManager(@NonNull OpenverseProxy proxy) {
        proxy.getEndpoint().getConnections().forEach(connection ->
                connection.getEventManager().register(this));
    }

    public void register(@NonNull P player) {
        playersByName.put(player.getName(), player);
        playersByConnection.put(player.getConnection(), player);
    }

    public P get(String name) {
        return playersByName.get(name);
    }

    public P get(Connection connection) {
        return playersByConnection.get(connection);
    }

    public Collection<P> get() {
        return playersByName.values();
    }

    public void unregister(@NonNull P player) {
        playersByName.remove(player.getName());
        playersByConnection.remove(player.getConnection());
    }

    public void clear() {
        playersByName.clear();
        playersByConnection.clear();
    }

    public abstract P createPlayer(Connection connection);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConnect(ConnectionOpenEvent event) {
        register(createPlayer(event.getConnection()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisconnect(ConnectionCloseEvent event) {
        unregister(get(event.getConnection()));
    }
}
