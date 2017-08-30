package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class PlayerChunkMap implements Listener {
    private final World world;
    private int radius;

    private Map<ChunkLocation, PlayerChunk> chunks = new HashMap<>();
    private Set<ServerPlayer> players = new HashSet<>();

    public PlayerChunkMap(World world, int radius) {
        this.world = world;
        this.radius = radius;
        Openverse.getEventManager().register(this);
    }

    public void addPlayer(ServerPlayer player, Chunk current) {
        ChunkLocation loc = current.getLocation();
        int minX = loc.x - radius;
        int maxX = loc.x + radius;
        int minY = loc.y - radius;
        int maxY = loc.y + radius;
        int minZ = loc.z - radius;
        int maxZ = loc.z + radius;

        for (int x = minX; x < maxX; x++)
            for (int y = minY; y < maxY; y++)
                for (int z = minZ; z < maxZ; z++)
                    addPlayer(new ChunkLocation(x, y, z), player);
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

        for (int x = minX; x < maxX; x++)
            for (int y = minY; y < maxY; y++)
                for (int z = minZ; z < maxZ; z++)
                    removePlayer(new ChunkLocation(x, y, z), player);
        players.remove(player);
    }

    public void onPlayerMove(ServerPlayer player, ChunkLocation old, ChunkLocation loc) {
        System.out.println("Player move: " + old + "->" + loc);
        int added = 0, removed = 0;

        Box oldBox = new Box(
                old.x - radius,
                old.y - radius,
                old.z - radius,
                old.x + radius,
                old.y + radius,
                old.z + radius
        );
        Box newBox = new Box(
                loc.x - radius,
                loc.y - radius,
                loc.z - radius,
                loc.x + radius,
                loc.y + radius,
                loc.z + radius
        );

        boolean apart = !oldBox.intersect(newBox);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    //Find toAdd
                    int cx = loc.x + x;
                    int cy = loc.y + y;
                    int cz = loc.z + z;

                    if (apart || !oldBox.isIn(cx, cy, cz)) {
                        addPlayer(new ChunkLocation(cx, cy, cz), player);
                        added++;
                    }

                    //Find toRemove
                    cx = old.x + x;
                    cy = old.y + y;
                    cz = old.z + z;
                    if (apart || !newBox.isIn(cx, cy, cz)) {
                        removePlayer(new ChunkLocation(cx, cy, cz), player);
                        removed++;
                    }
                }
            }
        }

        System.out.println("[Server]Player move: added:" + added + ", removed:" + removed + " (radius:" + radius + ", apart:" + apart + ")");
    }

    public void setRadius(int radius) {
        boolean adding = radius > this.radius;
        for (ServerPlayer player : players) {
            ChunkLocation loc = player.getLocation().getChunk().getLocation();
            for (int x = loc.x - radius; x > loc.x + radius; x++) {
                for (int y = loc.y - radius; y > loc.y + radius; y++) {
                    for (int z = loc.z - radius; z > loc.z + radius; z++) {
                        if (x + y + z > (adding ? this.radius : radius)) {
                            ChunkLocation l = new ChunkLocation(x, y, z);
                            if (adding)
                                addPlayer(l, player);
                            else
                                removePlayer(l, player);
                        }
                    }
                }
            }
        }
        this.radius = radius;
    }


    private void addPlayer(ChunkLocation location, ServerPlayer player) {
        getOrCreateChunk(location).addPlayer(player);
        player.getConnection().send(Openverse.channel(), new ChunkCreatePacket(world.getChunk(location)));
    }

    private void removePlayer(ChunkLocation location, ServerPlayer player) {
        chunks.computeIfPresent(location, (location1, playerChunk) -> {
            playerChunk.removePlayer(player);
            player.getConnection().send(Openverse.channel(), new ChunkDestroyPacket(location));
            return playerChunk.isEmpty() ? null : playerChunk;
        });
    }

    public PlayerChunk getChunk(ChunkLocation loc) {
        return chunks.get(loc);
    }

    public PlayerChunk getOrCreateChunk(ChunkLocation loc) {
        return chunks.computeIfAbsent(loc, PlayerChunk::new);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        System.out.println("[Server] Player move!");
        Location nl = e.getLocation();
        Location ol = e.getOldLocation();
        World nw = nl.getWorld();
        World ow = ol != null ? ol.getWorld() : null;
        if (ow != nw) {
            if (nw == world) {
                System.out.println("[Server] Player joined world: " + nw.getName());
                addPlayer((ServerPlayer) e.getPlayer(), nl.getChunk());
            } else if (ow == world) {
                System.out.println("[Server] Player left world: " + ow.getName());
                removePlayer((ServerPlayer) e.getPlayer());
            }
        } else {
            ChunkLocation ncl = nl.getChunk().getLocation();
            ChunkLocation ocl = ol != null ? ol.getChunk().getLocation() : null;
            if (nw == world && (ocl == null || !ncl.equals(ocl))) {
                System.out.println("[Server] Player changed chunk to: " + ncl.toString());
                onPlayerMove((ServerPlayer) e.getPlayer(), ocl, ncl);
            }
        }
    }
}
