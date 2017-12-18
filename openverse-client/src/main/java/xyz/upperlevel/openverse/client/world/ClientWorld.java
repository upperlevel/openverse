package xyz.upperlevel.openverse.client.world;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.world.PlayerBreakBlockPacket;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeHandSlotPacket;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.Chunk;
import xyz.upperlevel.openverse.world.chunk.ChunkPillar;
import xyz.upperlevel.openverse.world.chunk.HeightmapPacket;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

public class ClientWorld extends World implements PacketListener {
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
        setChunk(chk);
        chk.rebuildHeightMap();
        chk.updateSkylights();
        chk.updateNearbyChunksLights();
        updateBlockLights();
    }

    /**
     * Removes the received chunk from the current world.
     */
    @PacketHandler
    public void onChunkDestroy(Connection conn, ChunkDestroyPacket pkt) {
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
}
