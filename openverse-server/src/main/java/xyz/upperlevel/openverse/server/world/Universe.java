package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.PlayerBreakBlockPacket;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.*;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

/**
 * This is something similar to the World manager, in the sense that it stores all the worlds and their ids.
 */
public class Universe implements PacketListener {
    // the spawn location is just server side
    private Location spawn;
    private final Map<String, ServerWorld> worlds = new HashMap<>();

    public Universe() {
        Openverse.channel().register(this);
    }

    public Location getSpawn() {
        if (spawn == null) {
            ServerWorld w = new ServerWorld("world");
            addWorld(w);
            spawn = new Location(w, 0, 50, 0);
        }
        return spawn;
    }

    public World getWorld(String name) {
        return worlds.get(name);
    }

    public boolean addWorld(ServerWorld world) {
        worlds.put(world.getName().toLowerCase(Locale.ENGLISH), world);
        return false;
    }

    public boolean removeWorld(ServerWorld world) {
        return removeWorld(world.getName());
    }

    public boolean removeWorld(String name) {
        return worlds.remove(name.toLowerCase(Locale.ENGLISH)) != null;
    }

    public Collection<World> getWorlds() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public void onTick() {
        for (ServerWorld  world : worlds.values()) {
            world.onTick();
        }
    }

    @PacketHandler
    protected void onBlockBreak(Connection conn, PlayerBreakBlockPacket packet) {
        Player player = OpenverseServer.get().getPlayerManager().getPlayer(conn);
        // Apply changes to the world
        player.getWorld().setBlockState(packet.getX(), packet.getY(), packet.getZ(), AIR_STATE);
        // Resend the packet to the other players
        PlayerChunk c = ((ServerWorld)player.getWorld()).getChunkMap().getChunk(ChunkLocation.fromBlock(packet.getX(), packet.getY(), packet.getZ()));
        for (Player p : c.getPlayers()) {
            if (p != player) {
                p.getConnection().send(Openverse.channel(), packet);
            }
        }
    }
}
