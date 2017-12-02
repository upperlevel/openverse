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

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

public class ClientWorld extends World implements PacketListener {
    public ClientWorld(String name) {
        super(name);
        Openverse.channel().register(this);
    }

    @PacketHandler
    public void onChunkCreate(Connection conn, ChunkCreatePacket pkt) {
        setChunk(pkt.getX(), pkt.getY(), pkt.getZ(), pkt.resolveChunk(this));
    }

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
