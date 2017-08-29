package xyz.upperlevel.openverse.client.world;

import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.world.World;

public class ClientWorld extends World implements PacketListener {
    public ClientWorld(String name) {
        super(name); // may the client know world name?
        Openverse.getChannel().register(this);
    }

    @PacketHandler
    public void onChunkCreate(ChunkCreatePacket pkt) {
        setChunk(pkt.getLocation(), pkt.getChunk(this));
    }

    @PacketHandler
    public void onChunkDestroy(ChunkDestroyPacket pkt) {
        unloadChunk(pkt.getLocation());
    }
}
