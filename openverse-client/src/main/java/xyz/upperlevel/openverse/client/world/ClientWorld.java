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
        ChunkPillar plr = getChunkPillar(pkt.getX(), pkt.getZ());
        plr.setHeightmap(pkt.getHeightmap());
        Openverse.getLogger().severe("Received height-map for chunk pillar at " + plr.getX() + " " + plr.getZ());
    }

    /**
     * Adds the received chunk to the current world.
     */
    @PacketHandler
    public void onChunkCreate(Connection conn, ChunkCreatePacket pkt) {
        Chunk c = pkt.resolveChunk(this);
        setChunk(pkt.getX(), pkt.getY(), pkt.getZ(), c);
        c.appendBlockSkylights(true);
        Openverse.getLogger().severe("Built received chunk at " + c.getX() + " " + c.getY() + " " + c.getZ());
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
