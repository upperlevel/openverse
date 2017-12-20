package xyz.upperlevel.openverse.server.world.chunk;

import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Collection;

/**
 * This class represents a chunk containing a set of players.
 */
public interface PlayerChunkCache {
    Chunk getChunk();

    default ChunkLocation getLocation() {
        return getChunk().getLocation();
    }

    /**
     * Adds a new player to the chunk.
     */
    void addPlayer(Player player);

    /**
     * Removes the given player from the chunk.
     *
     * @param player the player
     */
    void removePlayer(Player player);

    Collection<Player> getPlayers();

    default boolean isEmpty() {
        return getPlayers().isEmpty();
    }
}
