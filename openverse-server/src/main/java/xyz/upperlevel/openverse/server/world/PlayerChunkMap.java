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
import xyz.upperlevel.openverse.world.chunk.*;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PlayerChunkMap implements Listener {
    private final ServerWorld world;
    private int radius;
    private ChunkMap<PlayerChunkCache> chunks = new ChunkMap<>();
    private Set<ServerPlayer> players = new HashSet<>();

    public PlayerChunkMap(ServerWorld world, int radius) {
        this.world = world;
        this.radius = radius;
        Openverse.getEventManager().register(this);
    }

    /**
     * Sends the chunk to the player.
     *
     * @param chunk  the chunk
     * @param player the player
     */
    public void sendChunkToPlayer(Chunk chunk, ServerPlayer player) {
        PlayerChunkCache pcc = chunks.get(chunk.getX(), chunk.getY(), chunk.getZ());
        if (pcc == null) {
            pcc = new PlayerChunkCache(chunk);
            chunks.put(chunk.getLocation(), pcc);
        }
        pcc.addPlayer(player);
        player.getConnection().send(Openverse.getChannel(), new ChunkCreatePacket(chunk));
    }

    /**
     * Sends all the chunks around the central one, radius specified
     * by {@link #getRadius()}, to the player.
     *
     * @param player       the player
     * @param centralChunk the central chunk
     */
    public void sendChunkViewToPlayer(ServerPlayer player, Chunk centralChunk) {
        ChunkLocation loc = centralChunk.getLocation();
        int minX = loc.x - radius;
        int maxX = loc.x + radius;
        int minY = loc.y - radius;
        int maxY = loc.y + radius;
        int minZ = loc.z - radius;
        int maxZ = loc.z + radius;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                // Generates heightmap
                ChunkPillar plr = world.getChunkPillar(x, z);
                world.getChunkGenerator().generateHeightmap(plr);
                // Ready to send heightmap
                player.getConnection().send(Openverse.getChannel(), new HeightmapPacket(x, z, plr.getHeightmap()));
                for (int y = minY; y <= maxY; y++) {
                    // Generates chunk
                    Chunk chk = plr.getChunk(y);
                    world.getChunkGenerator().generateChunk(chk);
                    // Ready to send chunk
                    sendChunkToPlayer(chk, player);
                }
            }
        }
        players.add(player);
    }

    /**
     * Destroys the chunk from the player.
     *
     * @param chunk  the chunk
     * @param player the player
     */
    public void destroyChunkFromPlayer(Chunk chunk, ServerPlayer player) {
        ChunkLocation loc = chunk.getLocation();
        PlayerChunkCache pcc = chunks.get(loc);
        if (pcc == null) {
            return;
        }
        pcc.removePlayer(player);
        player.getConnection().send(Openverse.getChannel(), new ChunkDestroyPacket(loc));
        if (pcc.isEmpty()) {
            chunks.remove(loc);
        }
    }

    /**
     * Destroys all the chunks around the central one, radius specified
     * by {@link #getRadius()}, from the player.
     *
     * @param player the player
     */
    public void destroyChunkViewFromPlayer(ServerPlayer player, Chunk centralChunk) {
        ChunkLocation loc = centralChunk.getLocation();
        int minX = loc.x - radius;
        int maxX = loc.x + radius;
        int minY = loc.y - radius;
        int maxY = loc.y + radius;
        int minZ = loc.z - radius;
        int maxZ = loc.z + radius;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    destroyChunkFromPlayer(world.getChunk(x, y, z), player);
                }
            }
        }
        players.remove(player);
    }

    /**
     * Changes the radius and updates the player views.
     *
     * @param radius the radius
     */
    public void setRadius(int radius) {
        boolean adding = radius > this.radius;
        for (ServerPlayer player : players) {
            Chunk chunk = player.getLocation().getChunk();
            for (int x = chunk.getX() - radius; x > chunk.getX() + radius; x++) {
                for (int y = chunk.getY() - radius; y > chunk.getY() + radius; y++) {
                    for (int z = chunk.getZ() - radius; z > chunk.getZ() + radius; z++) {
                        if (x + y + z > (adding ? this.radius : radius)) {
                            Chunk chk = world.getChunk(x, y, z);
                            if (adding) {
                                sendChunkToPlayer(chk, player);
                            } else {
                                destroyChunkFromPlayer(chk, player);
                            }
                        }
                    }
                }
            }
        }
        this.radius = radius;
    }

    protected void onPlayerMove(ServerPlayer player, ChunkLocation old, ChunkLocation loc) {
        Openverse.getLogger().info("Player move: " + old + "->" + loc);
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
            for (int z = -radius; z <= radius; z++) {
                // todo fix this!
                ChunkPillar plr = world.getChunkPillar(x, z);
                world.getChunkGenerator().generateHeightmap(plr);
                player.getConnection().send(Openverse.getChannel(), new HeightmapPacket(x, z, plr.getHeightmap()));
                //
                for (int y = -radius; y <= radius; y++) {
                    // Find toAdd
                    int cx = loc.x + x;
                    int cy = loc.y + y;
                    int cz = loc.z + z;
                    if (apart || !oldAabb.testPoint(cx, cy, cz)) {
                        sendChunkToPlayer(world.getChunk(cx, cy, cz), player);
                        added++;
                    }
                    // Find toRemove
                    cx = old.x + x;
                    cy = old.y + y;
                    cz = old.z + z;
                    if (apart || !newAabb.testPoint(cx, cy, cz)) {
                        destroyChunkFromPlayer(world.getChunk(cx, cy, cz), player);
                        removed++;
                    }
                }
            }
        }
        Openverse.getLogger().info("Player move: added:" + added + ", removed:" + removed + " (radius:" + radius + ", apart:" + apart + ")");
    }

    public PlayerChunkCache getChunk(ChunkLocation loc) {
        return chunks.get(loc);
    }

    public PlayerChunkCache getOrCreateChunk(int x, int y, int z) {
        PlayerChunkCache chunk = chunks.get(x, y, z);
        if (chunk == null) {
            chunk = new PlayerChunkCache(world.getChunk(x, y, z));
            chunks.put(x, y, z, chunk);
        }
        return chunk;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Location loc = e.getPlayer().getLocation();
        if (loc.getWorld() == world) {
            Openverse.getLogger().info("Player joined (" + world.getName() + ")");
            sendChunkViewToPlayer(e.getPlayer(), loc.getChunk());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Location loc = e.getPlayer().getLocation();
        if (loc.getWorld() == world) {
            Openverse.getLogger().info("Player quit (" + world.getName() + ")");
            destroyChunkViewFromPlayer(e.getPlayer(), e.getPlayer().getLocation().getChunk());
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
                Openverse.getLogger().info("Player joined world: " + nw.getName());
                sendChunkViewToPlayer((ServerPlayer) e.getPlayer(), nl.getChunk());
            } else if (ow == world) {
                Openverse.getLogger().info("Player left world: " + ow.getName());
                destroyChunkViewFromPlayer((ServerPlayer) e.getPlayer(), ol.getChunk());
            }
        } else {
            ChunkLocation ncl = nl.getChunk().getLocation();
            ChunkLocation ocl = ol.getChunk().getLocation();
            if (nw == world && (ocl == null || !ncl.equals(ocl))) {
                Openverse.getLogger().info("Player changed chunk to: " + ncl.toString());
                onPlayerMove((ServerPlayer) e.getPlayer(), ocl, ncl);
            }
        }
    }
}
