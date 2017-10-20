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
import xyz.upperlevel.openverse.network.world.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.server.event.PlayerQuitEvent;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.world.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener {
    private final Map<String, ServerPlayer> playersByName = new HashMap<>();
    private final Map<Connection, ServerPlayer> playersByConnection = new HashMap<>();

    /**
     * Starts listening for incoming connections.
     */
    public void start() {
        ((Server) Openverse.endpoint()).getEventManager().register(this);
    }

    /**
     * Stops listening for incoming connections.
     */
    public void close() {
        ((Server) Openverse.endpoint()).getEventManager().unregister(this);
    }

    public void register(@NonNull ServerPlayer player) {
        playersByName.put(player.getName(), player);
        playersByConnection.put(player.getConnection(), player);
    }

    public void unregister(ServerPlayer player) {
        playersByName.remove(player.getName());
        playersByConnection.remove(player.getConnection());
    }

    public ServerPlayer getPlayer(String name) {
        return playersByName.get(name);
    }

    public ServerPlayer getPlayer(Connection connection) {
        return playersByConnection.get(connection);
    }

    public Collection<ServerPlayer> getPlayers() {
        return playersByName.values();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onConnect(ConnectionOpenEvent event) {
        Location spawn = OpenverseServer.get().getUniverse().getSpawn();
        ServerPlayer sp = new ServerPlayer(spawn, "Hobbit", event.getConnection());
        register(sp);
        sp.getConnection().send(Openverse.channel(), new PlayerChangeWorldPacket(spawn.getWorld()));
        sp.getConnection().send(Openverse.channel(), new EntityTeleportPacket(sp));
        Openverse.getEventManager().call(new PlayerJoinEvent(sp));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisconnect(ConnectionCloseEvent event) {
        ServerPlayer player = getPlayer(event.getConnection());
        Openverse.getEventManager().call(new PlayerQuitEvent(player));
        unregister(player);
    }
}
