package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import xyz.upperlevel.openverse.server.world.ServerWorld;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkMap;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Getter
public class HashPlayerChunkStorage extends PlayerChunkStorage {
    private ChunkMap<HashPlayerChunkCache> chunks = new ChunkMap<>();

    public HashPlayerChunkStorage(ServerWorld world) {
        super(world);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChunkMap<PlayerChunkCache> getChunks() {
        return (ChunkMap)chunks;
    }

    @Override
    protected PlayerChunkCache create(ChunkLocation location) {
        return new HashPlayerChunkCache(getWorld().getChunk(location.x, location.y, location.z));
    }

    public static class HashPlayerChunkCache implements PlayerChunkCache {
        @Getter
        private final Chunk chunk;
        private Set<Player> players = new HashSet<>();

        public HashPlayerChunkCache(Chunk chunk) {
            this.chunk = chunk;
        }

        @Override
        public void addPlayer(Player player) {
            players.add(player);
        }

        @Override
        public void removePlayer(Player player) {
            players.remove(player);
        }

        @Override
        public Set<Player> getPlayers() {
            return Collections.unmodifiableSet(players);
        }
    }
}
