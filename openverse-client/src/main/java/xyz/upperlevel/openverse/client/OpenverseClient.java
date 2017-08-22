package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.impl.def.EventManager;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.render.Graphics;
import xyz.upperlevel.openverse.client.resource.ClientResourceManager;
import xyz.upperlevel.openverse.client.world.ClientUniverse;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.openverse.world.entity.Player;

@Getter
public class OpenverseClient implements OpenverseProxy {

    private static OpenverseClient instance;

    // connection
    private final Client endpoint;
    private final Channel channel;

    // world
    private final Player player;

    // resources
    private final ClientResourceManager resourceManager; // resources are loaded per universe

    // events
    private final EventManager eventManager;

    private final ClientUniverse universe;

    public OpenverseClient(@NonNull Client client) {
        Openverse.setProxy(this);
        instance = this; // for static access

        endpoint = client;
        Connection connection = client.getConnection();
        channel = new Channel("main").setProtocol(OpenverseProtocol.get());
        connection.setDefaultChannel(channel);

        player = new Player(new Location(null, 0, 0, 0), "my_player", connection);

        eventManager = new EventManager();
        resourceManager = new ClientResourceManager();

        universe = new ClientUniverse();
    }

    public static OpenverseClient get() {
        return instance;
    }

    @Override
    public ClientUniverse getUniverse() {
        return universe;
    }
}
