package xyz.upperlevel.openverse.server.world.chunk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.server.world.ServerWorld;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.chunk.ChunkMap;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.*;


@Getter
public class TinyPlayerChunkStorage extends PlayerChunkStorage implements Listener {
    protected static final int SIZE = Long.BYTES * 8;
    private final Runnable onOverflow;

    private ChunkMap<TinyPlayerChunkCache> chunks = new ChunkMap<>();
    private Player[] players = new Player[SIZE];
    @Getter
    private int nextId = 0;

    public TinyPlayerChunkStorage(ServerWorld world, Runnable onOverflow) {
        super(world);
        this.onOverflow = onOverflow;

        OpenverseServer.get().getPlayerManager().getPlayers().forEach(this::addPlayer);
        Openverse.getEventManager().register(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChunkMap<PlayerChunkCache> getChunks() {
        return (ChunkMap)chunks;
    }

    protected int getId(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                return i;
            }
        }
        throw new IllegalArgumentException("Cannot find player " + player);
    }

    public Player getPlayer(int id) {
        if (id >= nextId) {
            throw new IllegalArgumentException("Id not yet assigned " + id);
        }
        return players[id];
    }

    public void addPlayer(Player player) {
        Openverse.getLogger().severe("Adding player " + player.getName());
        if (nextId == SIZE) {
            onOverflow.run();
            destroy();
            return;
        }
        players[nextId++] = player;
    }

    protected void destroy() {
        Openverse.getEventManager().unregister(this);
    }

    @Override
    protected TinyPlayerChunkCache create(ChunkLocation location) {
        return new TinyPlayerChunkCache(getWorld().getChunk(location.x, location.y, location.z));
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        addPlayer(event.getPlayer());
    }

    public class TinyPlayerChunkCache implements PlayerChunkCache {
        @Getter
        private final Chunk chunk;
        private BitSet players = new BitSet(SIZE);

        public TinyPlayerChunkCache(Chunk chunk) {
            this.chunk = chunk;
        }

        @Override
        public void addPlayer(Player player) {
            players.set(getId(player));
        }

        @Override
        public void removePlayer(Player player) {
            players.clear(getId(player));
        }

        @Override
        public Collection<Player> getPlayers() {
            return new TinyChunkMapPlayerCollection(players);
        }

        @Override
        public boolean isEmpty() {
            return players.isEmpty();
        }
    }

    @RequiredArgsConstructor
    public class TinyChunkMapPlayerCollection extends AbstractCollection<Player> {
        private final BitSet handle;

        @Override
        public Iterator<Player> iterator() {
            return new Iterator<Player>() {
                private int currPlayer = handle.nextSetBit(0);

                protected void findNext() {
                    currPlayer = handle.nextSetBit(currPlayer + 1);
                }

                @Override
                public boolean hasNext() {
                    return currPlayer != -1;
                }

                @Override
                public Player next() {
                    if (!hasNext()) throw new NoSuchElementException();
                    Player player = getPlayer(currPlayer);
                    findNext();
                    return player;
                }
            };
        }

        @Override
        public int size() {
            return handle.cardinality();
        }
    }
}
