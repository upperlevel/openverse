package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.resource.ClientResourceManager;
import xyz.upperlevel.openverse.client.world.entity.ClientPlayer;
import xyz.upperlevel.openverse.world.entity.EntityManager;

@Getter
public class OpenverseClient implements OpenverseProxy {//TODO Implement

    private static OpenverseClient instance;

    // connection
    private final Client endpoint;
    private final Channel channel;

    // world
    private final ClientPlayer player;
    private final EntityManager entityManager;

    // resources
    private final ClientResourceManager resourceManager; // resources are loaded per universe


    // events
    private final EventManager eventManager;

    public OpenverseClient(@NonNull Client client) {
        instance = this; // for static access

        endpoint = client;
        Connection connection = client.getConnection();
        channel = new Channel("main").setProtocol(OpenverseProtocol.get());
        connection.setDefaultChannel(channel);

        player = new ClientPlayer("my_player", connection);
        entityManager = new EntityManager(this);

        eventManager = new EventManager();
        resourceManager = new ClientResourceManager();
    }

    public static OpenverseClient get() {
        return instance;
    }
}
