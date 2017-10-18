package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class PlayerChunk {
    private final Chunk chunk;

    //TODO too memory waste?
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player player) {
        players.add(player);
    }

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
