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
public class PlayerChunkMapListener extends PlayerChunkMap implements Listener {
    private final ServerWorld world;
    private PlayerChunkStorage chunks;
    private Set<ServerPlayer> players = new HashSet<>(); // Todo: update this bad asshole shit

    public PlayerChunkMapListener(ServerWorld world, int radius) {
        super(radius);
        this.world = world;
        chunks = new TinyPlayerChunkStorage(world, this::storageOverflow);
        Openverse.getEventManager().register(this);
    }

    private void storageOverflow() {
        PlayerChunkStorage old = chunks;
        chunks = new HashPlayerChunkStorage(world);
        chunks.copy(old);
    }

    public PlayerChunkCache getChunk(ChunkLocation loc) {
        return chunks.get(loc);
    }

    public void setRadius(int radius) {
        for (ServerPlayer player : players) {
            ChunkLocation central = player.getLocation().getChunk().getLocation();
            super.setRadius(player, central, radius);
        }
    }

    public void sendChunk(ServerPlayer player, Chunk chunk) {
        PlayerChunkCache pcc = chunks.getOrCreate(chunk.getLocation());
        pcc.addPlayer(player);
        player.getConnection().send(Openverse.getChannel(), new ChunkCreatePacket(chunk));
    }

    public void destroyChunk(ServerPlayer player, Chunk chunk) {
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

    @Override
    public void onChunkPillarSend(ServerPlayer player, int x, int z) {
        ChunkPillar plr = world.getOrLoadChunkPillar(x, z);
        player.getConnection().send(Openverse.getChannel(), new HeightmapPacket(plr));
    }

    @Override
    public void onChunkPillarDestroy(ServerPlayer player, int x, int z) {
        // todo player chunk pillar removal
    }

    @Override
    public void onChunkSend(ServerPlayer player, int x, int y, int z) {
        Chunk chk = world.getOrLoadChunk(x, y, z);
        sendChunk(player, chk);
    }

    @Override
    public void onChunkDestroy(ServerPlayer player, int x, int y, int z) {
        Chunk chk = world.getOrLoadChunk(x, y, z);
        destroyChunk(player, chk);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ServerPlayer pl = e.getPlayer();
        Location loc = pl.getLocation();
        if (loc.getWorld() == world) {
            sendChunkView(pl, loc.getChunkLocation());
            players.add(pl);
            Openverse.getLogger().info("Player \"" + pl.getName() + "\" joined the world \"" + world.getName() + "\"!");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        ServerPlayer pl = e.getPlayer();
        Location loc = pl.getLocation();
        if (loc.getWorld() == world) {
            destroyChunkView(pl, loc.getChunkLocation());
            players.remove(pl);
            Openverse.getLogger().info("Player \"" + pl.getName() + "\" quit the world \"" + world.getName() + "\"!");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Chunk oldChk = e.getOldLocation().getChunk();
        Chunk newChk = e.getLocation().getChunk();
        if (oldChk != newChk) {
            moveChunkView((ServerPlayer) e.getPlayer(), oldChk.getLocation(), newChk.getLocation());
        }
    }
}
