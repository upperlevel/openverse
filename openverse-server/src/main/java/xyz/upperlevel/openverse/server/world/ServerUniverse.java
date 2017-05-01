package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.network.GetChunkPacket;
import xyz.upperlevel.openverse.network.SendChunkPacket;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.chunk.Chunk;

import static xyz.upperlevel.openverse.Openverse.getChannel;

public class ServerUniverse extends Universe<ServerWorld> implements Listener {

    public ServerUniverse() {
        getChannel().getEventManager().register(this);
    }

    @EventHandler
    public void onGetChunk(Connection connection, GetChunkPacket event) {
        Chunk chunk = get(event.getWorld()).getChunk(event.getLocation());
        connection.send(getChannel(), new SendChunkPacket(chunk));
    }
}
