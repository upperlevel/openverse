package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventManager;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.PacketSide;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.game.ClientScene;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.console.log.OpenverseLogger;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.ulge.game.Stage;

import java.util.logging.Logger;

import static xyz.upperlevel.openverse.Openverse.PROTOCOL;

@Getter
public class OpenverseClient implements OpenverseProxy {
    private static OpenverseClient instance;

    private final Logger logger;

    private final Client endpoint;
    private final Channel channel;
    private final ClientResources resources; // resources are loaded per universe
    private final EventManager eventManager = new EventManager();
    private final EntityManager entityManager;

    public OpenverseClient(@NonNull Client client) {
        instance = this;
        Openverse.setProxy(this);

        endpoint = client;

        logger = new OpenverseLogger(this, "Client");

        Connection connection = client.getConnection();
        channel = new Channel("main").setProtocol(PROTOCOL.compile(PacketSide.CLIENT));
        connection.setDefaultChannel(channel);
        resources = new ClientResources(logger);
        entityManager = new EntityManager();
    }

    public void onTick() {
        entityManager.onTick();
    }

    public static OpenverseClient get() {
        return instance;
    }

    /**
     * This method is called from the launcher to initialize the {@link OpenverseClient}.
     * It will setup the client scene for the given stage.
     *
     * @param stage the stage to use
     */
    public void join(Stage stage) {
        stage.setScene(new ClientScene());
    }

    @Override
    public ClientResources getResources() {
        return resources;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
