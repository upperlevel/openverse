package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.server.resource.ServerResourceManager;
import xyz.upperlevel.openverse.server.world.ServerUniverse;
import xyz.upperlevel.openverse.world.entity.EntityManager;

public class OpenverseServer implements OpenverseProxy {

    private final Server server;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private final EventManager eventManager = new EventManager();

    @Getter
    private final ServerUniverse universe;

    @Getter
    private final ServerResourceManager resourceManager = new ServerResourceManager();

    @Getter
    private final EntityManager entityManager;

    @Getter
    private final ServerPlayerManager playerManager;

    // we don't know if the server is locally connected or use the network
    public OpenverseServer(@NonNull Server server) {
        this.server = server;
        server.setDefaultChannel(channel);

        universe      = new ServerUniverse(this);
        entityManager = new EntityManager();
        playerManager = new ServerPlayerManager(this);
    }

    public void start() {
        resourceManager.load();
    }

    public void stop() {
    }

    @Override
    public Server getEndpoint() {
        return server;
    }
}
