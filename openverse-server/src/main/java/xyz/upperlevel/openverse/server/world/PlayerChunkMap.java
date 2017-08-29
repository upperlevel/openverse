package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerChunkMap {
    private final World handle;
    private int radius;

    private Map<ChunkLocation, PlayerChunk> chunks = new HashMap<>();
    private Set<Player> players = new HashSet<>();

    public PlayerChunkMap(World handle, int radius) {
        this.handle = handle;
        this.radius = radius;
    }

    public void addPlayer(Player player) {
        ChunkLocation loc = player.getLocation().getChunk().getLocation();
        final int minX = loc.x - radius;
        final int maxX = loc.x + radius;
        final int minY = loc.y - radius;
        final int maxY = loc.y + radius;
        final int minZ = loc.z - radius;
        final int maxZ = loc.z + radius;

        for(int x = minX; x < maxX; x++)
            for(int y = minY; y < maxY; y++)
                for(int z = minZ; z < maxZ; z++)
                    addPlayer(ChunkLocation.of(x, y, z), player);
        players.add(player);
    }

    public void removePlayer(Player player) {
        ChunkLocation loc = player.getLocation().getChunk().getLocation();
        final int minX = loc.x - radius;
        final int maxX = loc.x + radius;
        final int minY = loc.y - radius;
        final int maxY = loc.y + radius;
        final int minZ = loc.z - radius;
        final int maxZ = loc.z + radius;

        for(int x = minX; x < maxX; x++)
            for(int y = minY; y < maxY; y++)
                for(int z = minZ; z < maxZ; z++)
                    removePlayer(ChunkLocation.of(x, y, z), player);
        players.remove(player);
    }

    public void onPlayerMove(Player player, ChunkLocation old, ChunkLocation loc) {
        final int side = radius*2;

        Box oldBox = new Box(
                old.x - radius,
                old.y - radius,
                old.z - radius,
                side,
                side,
                side
        );
        Box newBox = new Box(
                loc.x - radius,
                loc.y - radius,
                loc.z - radius,
                side,
                side,
                side
        );

        final boolean apart = !oldBox.intersect(newBox);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    //Find toAdd
                    int cx = loc.x + x;
                    int cy = loc.y + y;
                    int cz = loc.z + z;

                    if(apart || !oldBox.isIn(cx, cy, cz))
                        addPlayer(ChunkLocation.of(cx, cy, cz), player);

                    //Find toRemove
                    cx = old.x + x;
                    cy = old.y + y;
                    cz = old.z + z;
                    if(apart || !newBox.isIn(cx, cy, cz))
                        removePlayer(loc, player);
                }
            }
        }
    }

    public void setRadius(int radius) {
        final boolean adding = radius > this.radius;

        for(Player player : players) {
            ChunkLocation loc = player.getLocation().getChunk().getLocation();
            for(int x = loc.x - radius; x > loc.x + radius; x++) {
                for(int y = loc.y - radius; y > loc.y + radius; y++) {
                    for(int z = loc.z - radius; z > loc.z + radius; z++) {
                        if(x + y + z > (adding ? this.radius : radius)) {
                            final ChunkLocation l = ChunkLocation.of(x, y, z);
                            if(adding)
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


    private void addPlayer(ChunkLocation location, Player player) {
        getOrCreateChunk(location).addPlayer(player);
    }

    private void removePlayer(ChunkLocation location, Player player) {
        chunks.computeIfPresent(location, (location1, playerChunk) -> {
            playerChunk.removePlayer(player);
            return playerChunk.isEmpty() ? null : playerChunk;
        });
    }

    public PlayerChunk getChunk(ChunkLocation loc) {
        return chunks.get(loc);
    }

    public PlayerChunk getOrCreateChunk(ChunkLocation loc) {
        return chunks.computeIfAbsent(loc, PlayerChunk::new);
    }


    @EventHandler()
    public void onPlayerJoin(PlayerJoinEvent event) {
        addPlayer(event.getPlayer());
    }

    @EventHandler()
    public void onPlayerQuit(PlayerJoinEvent event) {
        removePlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        if(!event.isCancelled()) {
            ChunkLocation loc = event.getLocation().getChunk().getLocation();
            ChunkLocation oldLoc = event.getOldLocation().getChunk().getLocation();
            if(loc != event.getOldLocation().getChunk().getLocation())
                onPlayerMove(event.getPlayer(), oldLoc, loc);
        }
    }
}
