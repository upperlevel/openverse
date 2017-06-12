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
import xyz.upperlevel.openverse.client.world.ClientUniverse;
import xyz.upperlevel.openverse.client.world.entity.ClientPlayer;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

public class OpenverseClient implements OpenverseProxy {//TODO Implement

    @Getter
    private final Client endpoint;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private final ClientUniverse universe;

    @Getter
    // the only player to render
    private final ClientPlayer player;

    @Getter
    private final EntityManager entityManager;

    @Getter
    // resources are loaded per universe
    private final ClientResourceManager resourceManager;

    @Getter
    private final EventManager eventManager;

    public OpenverseClient(@NonNull Client client) {
        this.endpoint = client;

        // if connection is closed refuse
        Connection connection = client.getConnection();
        connection.setDefaultChannel(channel);

        universe = new ClientUniverse(this);
        player = new ClientPlayer("my_player", connection);
        entityManager = new EntityManager(this);
        eventManager = new EventManager();
        resourceManager = new ClientResourceManager();
    }
}
