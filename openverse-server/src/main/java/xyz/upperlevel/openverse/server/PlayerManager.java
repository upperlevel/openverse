package xyz.upperlevel.openverse.server;

import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.event.ConnectionCloseEvent;
import xyz.upperlevel.hermes.event.ConnectionOpenEvent;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.world.ServerPlayer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {
    private final Map<String, ServerPlayer> playersByName = new HashMap<>();
    private final Map<Connection, ServerPlayer> playersByConnection = new HashMap<>();

    public PlayerManager() {
        ((Server) Openverse.endpoint()).getEventManager().register(this);
    }

    public void register(@NonNull ServerPlayer player) {
        playersByName.put(player.getName(), player);
        playersByConnection.put(player.getConnection(), player);
    }

    public ServerPlayer get(String name) {
        return playersByName.get(name);
    }

    public ServerPlayer get(Connection connection) {
        return playersByConnection.get(connection);
    }

    public Collection<ServerPlayer> get() {
        return playersByName.values();
    }

    public void unregister(@NonNull ServerPlayer player) {
        playersByName.remove(player.getName());
        playersByConnection.remove(player.getConnection());
    }

    public void clear() {
        playersByName.clear();
        playersByConnection.clear();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConnect(ConnectionOpenEvent event) {
        System.out.println("[Server] New connection reached!");
        ServerPlayer sp = new ServerPlayer("Hobbit", event.getConnection());
        register(sp);
        System.out.println("[Server] Instantiated a new player, spawning him.");
        sp.setLocation(OpenverseServer.get().getUniverse().getSpawn());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisconnect(ConnectionCloseEvent event) {
        unregister(get(event.getConnection()));
    }
}
