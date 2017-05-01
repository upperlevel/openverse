package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.hermes.event.ConnectionEventManager;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.network.EntityTeleportPacketReceiveListener;
import xyz.upperlevel.openverse.client.network.UniverseInfoPacketReceiveListener;
import xyz.upperlevel.openverse.client.resource.ClientResourceManager;
import xyz.upperlevel.openverse.client.world.ClientUniverse;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.client.world.WorldViewer;
import xyz.upperlevel.openverse.network.EntityTeleportPacket;
import xyz.upperlevel.openverse.client.world.entity.ClientPlayer;
import xyz.upperlevel.openverse.network.GetUniversePacket;
import xyz.upperlevel.openverse.network.SendUniversePacket;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.entity.EntityManager;
import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;

import java.util.Collection;

import static java.util.Collections.singletonList;

public class OpenverseClient implements OpenverseProxy, Scene {//TODO Implement

    private final Client client;

    @Getter
    private final WorldViewer viewer;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private final ClientUniverse universe;

    @Getter
    private final ClientPlayerManager playerManager;

    @Getter
    private final EntityManager entityManager;

    @Getter
    private ClientResourceManager resourceManager = new ClientResourceManager();

    public OpenverseClient(@NonNull Client client) {
        this.client = client;

        // if connection is closed refuse
        Connection conn = client.getConnection();
        conn.setDefaultChannel(channel);
        if (!conn.isOpen())
            throw new IllegalStateException("Client connection is closed");

        universe      = new ClientUniverse(this);
        playerManager = new ClientPlayerManager(this);
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
        Uniformer uniformer = null; // todo get uniformer somewhere
        player.render(uniformer);
    }

    @Override
    public Client getEndpoint() {
        return client;
    }
}
