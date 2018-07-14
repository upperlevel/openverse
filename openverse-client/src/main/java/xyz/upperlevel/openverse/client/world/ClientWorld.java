package xyz.upperlevel.openverse.client.world;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.network.world.PlayerBreakBlockPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeHandSlotPacket;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;
import xyz.upperlevel.openverse.world.chunk.HeightmapPacket;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

public class ClientWorld extends World implements PacketListener {
    public static final long BUILD_TIMEOUT = 10000000;
    public static final int MAX_PENDING_QUEUE_SIZE = 512*4;
    private boolean gameStarted = false;
    private BlockingQueue<Chunk> buildPendingChunks = new ArrayBlockingQueue<>(MAX_PENDING_QUEUE_SIZE);

    public ClientWorld(String name) {
        super(name);
        Openverse.getChannel().register(this);
    }

    /**
     * Sets the heightmap to the specified {@link ChunkPillar} and updates the skylights.
     */
    @PacketHandler
    public void onHeightmapReceive(Connection conn, HeightmapPacket pkt) {
        ChunkPillar plr = new ChunkPillar(this, pkt.getX(), pkt.getZ());
        plr.setHeightmap(pkt.getHeightmap());
        setChunkPillar(plr);
    }

    /**
     * Adds the received chunk to the current world.
     */
    @PacketHandler
    public void onChunkCreate(Connection conn, ChunkCreatePacket pkt) {
        ChunkPillar plr = getChunkPillar(pkt.getX(), pkt.getZ());
        if (plr == null) {
            Openverse.getLogger().warning("Chunk received at x=" + pkt.getX() + " y=" + pkt.getY() + " z=" + pkt.getZ() + " but there is no pillar!");
            return;
        }
        Chunk chk = new Chunk(plr, pkt.getY());
        pkt.setBlockStates(chk);
        if (gameStarted && buildPendingChunks.size() < MAX_PENDING_QUEUE_SIZE) {
            buildPendingChunks.add(chk);
        } else {
            buildChunk(chk);
        }
    }

    /**
     * Removes the received chunk from the current world.
     */
    @PacketHandler
    public void onChunkDestroy(Connection conn, ChunkDestroyPacket pkt) {
        if (getChunk(pkt.getX(), pkt.getY(), pkt.getZ()) == null) {
            // Chunk not loaded, still pending
            removePendingChunk(pkt.getX(), pkt.getY(), pkt.getZ());
        }
        unloadChunk(pkt.getX(), pkt.getY(), pkt.getZ());
    }

    @PacketHandler
    public void onBlockBreak(Connection conn, PlayerBreakBlockPacket packet) {
        setBlockState(packet.getX(), packet.getY(), packet.getZ(), AIR_STATE);
    }

    @PacketHandler
    public void onPlayerChangeSlot(Connection conn, PlayerChangeHandSlotPacket packet) {
        OpenverseClient.get().getPlayer().getInventory().unsafeSetHandSlot(packet.getNewHandSlotId());
    }

    @Override
    public void onTick() {
        gameStarted = true;
        long chunkBuildTimeout = System.nanoTime() + BUILD_TIMEOUT;
        while (!buildPendingChunks.isEmpty()) {
            buildChunk(buildPendingChunks.poll());
            if (System.nanoTime() > chunkBuildTimeout) break;
        }
        super.onTick();
    }

    public void buildChunk(Chunk chk) {
        setChunk(chk);
        chk.reloadAllLightBlocks();
        chk.rebuildHeightMap();
        chk.updateSkylights();
        chk.updateNearbyChunksLights();
        //updateAllLights();
        flushLightChunkUpdates();
    }

    public boolean removePendingChunk(int x, int y, int z) {
        Iterator<Chunk> i = buildPendingChunks.iterator();
        while (i.hasNext()) {
            Chunk c = i.next();
            if (c.getX() == x && c.getY() == y && c.getZ() == z) {
                i.remove();
                return true;
            }
        }
        return false;
    }
}
