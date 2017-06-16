package xyz.upperlevel.openverse.server;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.event.impl.ConnectionCloseEvent;
import xyz.upperlevel.hermes.event.impl.ConnectionOpenEvent;
import xyz.upperlevel.hermes.server.Server;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.server.resource.ServerResourceManager;
import xyz.upperlevel.openverse.server.world.ServerUniverse;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

public class OpenverseServer implements OpenverseProxy, Listener {


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
    private final PlayerManager playerManager;

    // we don't know if the server is locally connected or use the network
    public OpenverseServer(@NonNull Server server) {
        this.server = server;
        server.setDefaultChannel(channel);

        universe      = new ServerUniverse(this);
        entityManager = new EntityManager();
        playerManager = new PlayerManager();
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
