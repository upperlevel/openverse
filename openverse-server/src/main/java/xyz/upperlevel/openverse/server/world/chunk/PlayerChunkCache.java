package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a chunk containing a set of players.
 */
@Getter
public class PlayerChunkCache {
    private final Chunk chunk;
    private Set<Player> players = new HashSet<>();

    public PlayerChunkCache(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Adds a new player to the chunk.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes the given player from the chunk.
     *
     * @param player the player
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public ChunkLocation getLocation() {
        return chunk.getLocation();
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }
}
