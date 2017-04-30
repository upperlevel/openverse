package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.entity.Player;

import java.util.List;

public class OpenverseServer implements OpenverseProxy {

    @Getter
    private final Server server;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    // we don't know if the server is locally connected or use the network
    public OpenverseServer(@NonNull Server server) {
        this.server = server;
        server.setDefaultChannel(channel);
    }

    public void start() {
        // when start we have to set up all packets listeners
    }

    public void stop() {
        // when stop we have to delete all listeners
    }

    @Override
    public ResourceManager getResourceManager() {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public Universe getUniverse() {
        return null;
    }

    @Override
    public Server getEndpoint() {
        return server;
    }
}
