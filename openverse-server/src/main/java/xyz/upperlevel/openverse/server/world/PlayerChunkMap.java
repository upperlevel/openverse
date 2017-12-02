package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import org.joml.AABBf;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.server.event.PlayerQuitEvent;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkMap;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PlayerChunkMap implements Listener {
    private final World world;
    private int radius;

    private ChunkMap<PlayerChunk> chunks = new ChunkMap<>();
    private Set<ServerPlayer> players = new HashSet<>();

    public PlayerChunkMap(World world, int radius) {
        this.world = world;
        this.radius = radius;
        Openverse.getEventManager().register(this);
    }

    public void addPlayer(ServerPlayer player, Chunk current) {
        Openverse.logger().fine("Add player: " + player);
        ChunkLocation loc = current.getLocation();
        int minX = loc.x - radius;
        int maxX = loc.x + radius;
        int minY = loc.y - radius;
        int maxY = loc.y + radius;
        int minZ = loc.z - radius;
        int maxZ = loc.z + radius;

        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                for (int z = minZ; z <= maxZ; z++)
                    addPlayer(x, y, z, player);
        players.add(player);
    }

    public void removePlayer(ServerPlayer player) {
        ChunkLocation loc = player.getLocation().getChunk().getLocation();
        int minX = loc.x - radius;
        int maxX = loc.x + radius;
        int minY = loc.y - radius;
        int maxY = loc.y + radius;
        int minZ = loc.z - radius;
        int maxZ = loc.z + radius;

        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                for (int z = minZ; z <= maxZ; z++)
                    removePlayer(x, y, z, player);
        players.remove(player);
    }

    public void onPlayerMove(ServerPlayer player, ChunkLocation old, ChunkLocation loc) {
        Openverse.logger().info("Player move: " + old + "->" + loc);
        int added = 0, removed = 0;

        AABBf oldAabb = new AABBf(
                old.x - radius,
                old.y - radius,
                old.z - radius,
                old.x + radius,
                old.y + radius,
                old.z + radius
        );
        AABBf newAabb = new AABBf(
                loc.x - radius,
                loc.y - radius,
                loc.z - radius,
                loc.x + radius,
                loc.y + radius,
                loc.z + radius
        );

        boolean apart = !oldAabb.testAABB(newAabb);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    //Find toAdd
                    int cx = loc.x + x;
                    int cy = loc.y + y;
                    int cz = loc.z + z;

                    if (apart || !oldAabb.testPoint(cx, cy, cz)) {
                        addPlayer(cx, cy, cz, player);
                        added++;
                    }

                    //Find toRemove
                    cx = old.x + x;
                    cy = old.y + y;
                    cz = old.z + z;
                    if (apart || !newAabb.testPoint(cx, cy, cz)) {
                        removePlayer(cx, cy, cz, player);
                        removed++;
                    }
                }
            }
        }

        Openverse.logger().info("Player move: added:" + added + ", removed:" + removed + " (radius:" + radius + ", apart:" + apart + ")");
    }

    public void setRadius(int radius) {
        boolean adding = radius > this.radius;
        for (ServerPlayer player : players) {
            Chunk chunk = player.getLocation().getChunk();
            for (int x = chunk.getX() - radius; x > chunk.getX() + radius; x++) {
                for (int y = chunk.getY() - radius; y > chunk.getY() + radius; y++) {
                    for (int z = chunk.getZ() - radius; z > chunk.getZ() + radius; z++) {
                        if (x + y + z > (adding ? this.radius : radius)) {
                            if (adding)
                                addPlayer(x, y, z, player);
                            else
                                removePlayer(x, y, z, player);
                        }
                    }
                }
            }
        }
        this.radius = radius;
    }


    private void addPlayer(int x, int y, int z, ServerPlayer player) {
        PlayerChunk chunk = getOrCreateChunk(x, y, z);
        chunk.addPlayer(player);
        player.getConnection().send(Openverse.channel(), new ChunkCreatePacket(chunk.getChunk()));
    }

    private void removePlayer(int x, int y, int z, ServerPlayer player) {
        PlayerChunk chunk = chunks.get(x, y, z);
        if (chunk == null) {
            return;
        }
        chunk.removePlayer(player);
        player.getConnection().send(Openverse.channel(), new ChunkDestroyPacket(x, y, z));
        if (chunk.isEmpty()) {
            chunks.remove(x, y, z);
        }
    }

    public PlayerChunk getChunk(ChunkLocation loc) {
        return chunks.get(loc);
    }

    public PlayerChunk getOrCreateChunk(int x, int y, int z) {
        PlayerChunk chunk = chunks.get(x, y, z);
        if (chunk == null) {
            chunk = new PlayerChunk(world.getChunk(x, y, z));
            chunks.put(x, y, z, chunk);
        }
        return chunk;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Location loc = e.getPlayer().getLocation();
        if (loc.getWorld() == world) {
            Openverse.logger().info("Player joined (" + world.getName() + ")");
            addPlayer(e.getPlayer(), loc.getChunk());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Location loc = e.getPlayer().getLocation();
        if (loc.getWorld() == world) {
            Openverse.logger().info("Player quit (" + world.getName() + ")");
            removePlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Location nl = e.getLocation();
        Location ol = e.getOldLocation();
        World nw = nl.getWorld();
        World ow = ol.getWorld();
        if (ow != nw) {
            if (nw == world) {
                Openverse.logger().info("Player joined world: " + nw.getName());
                addPlayer((ServerPlayer) e.getPlayer(), nl.getChunk());
            } else if (ow == world) {
                Openverse.logger().info("Player left world: " + ow.getName());
                removePlayer((ServerPlayer) e.getPlayer());
            }
        } else {
            ChunkLocation ncl = nl.getChunk().getLocation();
            ChunkLocation ocl = ol.getChunk().getLocation();
            if (nw == world && (ocl == null || !ncl.equals(ocl))) {
                Openverse.logger().info("Player changed chunk to: " + ncl.toString());
                onPlayerMove((ServerPlayer) e.getPlayer(), ocl, ncl);
            }
        }
    }
}
