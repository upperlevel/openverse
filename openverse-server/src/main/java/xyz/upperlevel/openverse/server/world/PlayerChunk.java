package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PlayerChunk {

    @Getter
    private final ChunkLocation loc;

    //TODO too memory consumption?
    private Set<Player> players = new HashSet<>();


    public PlayerChunk(ChunkLocation loc) {
        this.loc = loc;
    }


    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(players);
    }
}
