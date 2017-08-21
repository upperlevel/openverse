package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.server.resource.ServerResourceManager;
import xyz.upperlevel.openverse.server.world.ServerUniverse;

@Getter
public class OpenverseServer implements OpenverseProxy, Listener {
    private final Server endpoint;
    private final Channel channel;
    private final ServerUniverse universe;
    private final EventManager eventManager = new EventManager();
    private final ServerResourceManager resourceManager = new ServerResourceManager();
    private final PlayerManager playerManager;

    // we don't know if the server is locally connected or use the network
    public OpenverseServer(@NonNull Server server) {
        this.endpoint = server;
        this.channel = new Channel("main").setProtocol(OpenverseProtocol.get());
        this.endpoint.setDefaultChannel(channel);
        this.universe = new ServerUniverse(this);
        this.playerManager = new PlayerManager();
    }

    public void start() {
        resourceManager.load();
    }

    public void stop() {
    }
}
