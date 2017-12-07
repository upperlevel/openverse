package xyz.upperlevel.openverse.server;

import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.event.ConnectionCloseEvent;
import xyz.upperlevel.hermes.event.ConnectionOpenEvent;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.item.ItemTypes;
import xyz.upperlevel.openverse.network.inventory.PlayerInventoryActionPacket;
import xyz.upperlevel.openverse.network.world.PlayerBreakBlockPacket;
import xyz.upperlevel.openverse.network.world.PlayerUseItemPacket;
import xyz.upperlevel.openverse.network.world.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeHandSlotPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.server.event.PlayerQuitEvent;
import xyz.upperlevel.openverse.server.world.PlayerChunk;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.server.world.ServerWorld;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;

import javax.swing.text.html.ListView;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager implements Listener, PacketListener {
    private final Map<String, ServerPlayer> playersByName = new HashMap<>();
    private final Map<Connection, ServerPlayer> playersByConnection = new HashMap<>();

    /**
     * Starts listening for incoming connections.
     */
    public void start() {
        OpenverseServer.get().getEndpoint().getEventManager().register(this);
        Openverse.channel().register(this);
    }

    /**
     * Stops listening for incoming connections.
     */
    public void close() {
        OpenverseServer.get().getEndpoint().getEventManager().unregister(this);
        Openverse.channel().unregister(this);
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

        //Add a block of dirt in his hand
        //sp.getInventory().setHandItem(new ItemStack(Openverse.resources().itemTypes().entry("dirt")));
        {
            PlayerInventory inv = sp.getInventory();
            for (int i = 0; i < inv.getSize(); i++) {
                inv.get(i).swap(new ItemStack(i % 2 == 0 ? ItemTypes.DIRT : ItemTypes.GRASS));
            }
        }
        sp.updateInventory();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDisconnect(ConnectionCloseEvent event) {
        ServerPlayer player = getPlayer(event.getConnection());
        Openverse.getEventManager().call(new PlayerQuitEvent(player));
        unregister(player);
    }

    @PacketHandler
    public void onBlockBreak(Connection conn, PlayerBreakBlockPacket packet) {
        Player player = OpenverseServer.get().getPlayerManager().getPlayer(conn);
        // Apply changes to the world
        player.breakBlock(packet.getX(), packet.getY(), packet.getZ(), false);
        // Resend the packet to the other players
        PlayerChunk c = ((ServerWorld)player.getWorld()).getChunkMap().getChunk(ChunkLocation.fromBlock(packet.getX(), packet.getY(), packet.getZ()));
        for (Player p : c.getPlayers()) {
            if (p != player) {
                p.getConnection().send(Openverse.channel(), packet);
            }
        }
    }

    @PacketHandler
    public void onItemUse(Connection conn, PlayerUseItemPacket packet) {
        Player player = OpenverseServer.get().getPlayerManager().getPlayer(conn);
        // Apply changes to the world
        player.useItemInHand(packet.getX(), packet.getY(), packet.getZ(), packet.getFace(), false);
        // Resend the packet to the other players (TODO: fix no entity specified in the packet for other players in multiplayer)
        PlayerChunk c = ((ServerWorld)player.getWorld()).getChunkMap().getChunk(ChunkLocation.fromBlock(packet.getX(), packet.getY(), packet.getZ()));
        for (Player p : c.getPlayers()) {
            if (p != player) {
                p.getConnection().send(Openverse.channel(), packet);
            }
        }
    }

    @PacketHandler
    public void onInventoryAction(Connection conn, PlayerInventoryActionPacket packet) {
        Player player = OpenverseServer.get().getPlayerManager().getPlayer(conn);
        //TODO: add inventory interaction
        Openverse.logger().info("Inventory action received (slot:" + packet.getSlotId() + ", action:" + packet.getAction().name() + ")");
    }

    @PacketHandler
    public void onPlayerChangeHandSlot(Connection conn, PlayerChangeHandSlotPacket packet) {
        Player player = OpenverseServer.get().getPlayerManager().getPlayer(conn);
        player.getInventory().unsafeSetHandSlot(packet.getNewHandSlotId());
    }
}
