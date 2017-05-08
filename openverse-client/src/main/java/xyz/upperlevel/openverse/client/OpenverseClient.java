package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
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

public class OpenverseClient implements OpenverseProxy, Scene {//TODO Implement

    private final Client client;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private final ClientUniverse universe;

    @Getter
    private final ClientPlayer player;

    @Getter
    private final EntityManager entityManager;

    @Getter
    private ClientResourceManager resourceManager = new ClientResourceManager();

    public OpenverseClient(@NonNull Client client) {
        this.client = client;

        // if connection is closed refuse
        Connection connection = client.getConnection();
        connection.setDefaultChannel(channel);
        if (!connection.isOpen())
            throw new IllegalStateException("Client connection is closed!");

        universe      = new ClientUniverse(this);
        player        = new ClientPlayer("my_player", connection);
        entityManager = new EntityManager(this);
    }

    @Override
    public void onEnable(Scene prev) {
        resourceManager.load(); // loads LOCAL resources first
    }

    @Override
    public void onDisable(Scene prev) {
        try {
            client.stop();
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void onRender() {
        Uniformer uniformer = null; // todo getWorld uniformer somewhere
        player.render(uniformer);
    }

    @Override
    public Client getEndpoint() {
        return client;
    }
}
