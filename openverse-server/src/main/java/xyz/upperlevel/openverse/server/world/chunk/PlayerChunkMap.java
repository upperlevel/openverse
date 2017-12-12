package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.server.event.PlayerQuitEvent;
import xyz.upperlevel.openverse.server.world.ServerPlayer;
import xyz.upperlevel.openverse.server.world.ServerWorld;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.chunk.*;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PlayerChunkMap extends ChunkMapHandler implements Listener {
    private final ServerWorld world;
    private ChunkMap<PlayerChunkCache> chunks = new ChunkMap<>();
    private Set<ServerPlayer> players = new HashSet<>(); // Todo: update this bad asshole shit

    public PlayerChunkMap(ServerWorld world, int radius) {
        super(radius);
        this.world = world;
        Openverse.getEventManager().register(this);
    }

    public void setRadius(int radius) {
        for (ServerPlayer player : players) {
            ChunkLocation central = player.getLocation().getChunk().getLocation();
            super.setRadius(player, central, radius);
        }
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
     * Sends all the chunks around the central one, radius specified
     * by {@link #getRadius()}, to the player.
     *
     * @param player  the player
     * @param central the central chunk
     */
    public void sendChunkViewToPlayer(ServerPlayer player, Chunk central) {
        super.addChunkView(player, central.getLocation());
        // ChunkMapHandler.addChunkView(player, central.getLocation(), radius, manipulator);
        players.add(player);
    }

    /**
     * Destroys all the chunks around the central one, radius specified
     * by {@link #getRadius()}, from the player.
     *
     * @param player the player
     */
    public void destroyChunkViewFromPlayer(ServerPlayer player, Chunk central) {
        super.removeChunkView(player, central.getLocation());
        players.remove(player);
    }

    public void moveChunkView(ServerPlayer player, ChunkLocation from, ChunkLocation to) {
        super.moveChunkView(player, from, to);
    }

    @Override
    public void onChunkPillarAdd(ServerPlayer player, int x, int z) {
        ChunkPillar plr = world.getChunkPillar(x, z);
        world.getChunkGenerator().generateHeightmap(plr);
        player.getConnection().send(Openverse.getChannel(), new HeightmapPacket(plr));
    }

    @Override
    public void onChunkPillarRemove(ServerPlayer player, int x, int z) {
        ChunkPillar plr = world.getChunkPillar(x, z);
        // TODO chunk pillar removal packet ?
    }

    @Override
    public void onChunkAdd(ServerPlayer player, int x, int y, int z) {
        Chunk chk = world.getChunk(x, y, z);
        world.getChunkGenerator().generateChunk(chk);
        sendChunkToPlayer(chk, player);
    }

    @Override
    public void onChunkRemove(ServerPlayer player, int x, int y, int z) {
        Chunk chk = world.getChunk(x, y, z);
        destroyChunkFromPlayer(chk, player);
    }

    public PlayerChunkCache getChunk(ChunkLocation loc) {
        return chunks.get(loc);
    }

    /*
    public PlayerChunkCache getOrCreateChunk(int x, int y, int z) {
        PlayerChunkCache chunk = chunks.get(x, y, z);
        if (chunk == null) {
            chunk = new PlayerChunkCache(world.getChunk(x, y, z));
            chunks.put(x, y, z, chunk);
        }
        return chunk;
    }
    */

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Location loc = e.getPlayer().getLocation();
        if (loc.getWorld() == world) {
            sendChunkViewToPlayer(e.getPlayer(), loc.getChunk());
            Openverse.getLogger().info("Player joined (" + world.getName() + ")");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Location loc = e.getPlayer().getLocation();
        if (loc.getWorld() == world) {
            destroyChunkViewFromPlayer(e.getPlayer(), e.getPlayer().getLocation().getChunk());
            Openverse.getLogger().info("Player quit (" + world.getName() + ")");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // Todo: player world change not managed here
        Chunk oldChk = e.getOldLocation().getChunk();
        Chunk newChk = e.getLocation().getChunk();
        if (oldChk != newChk) {
            moveChunkView((ServerPlayer) e.getPlayer(), oldChk.getLocation(), newChk.getLocation());
        }
    }
}
