package xyz.upperlevel.openverse.client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.hermes.client.Client;
import xyz.upperlevel.openverse.OpenverseProtocol;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.network.GetUniversePacket;
import xyz.upperlevel.openverse.network.UniverseInfoPacket;
import xyz.upperlevel.openverse.resource.ResourceManager;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.World;

import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class OpenverseClient implements OpenverseProxy {//TODO Implement

    @Getter
    private final Client client;

    @Getter
    private final Universe<ClientWorld> universe;

    @Getter
    private final Channel channel = new Channel("main")
            .setProtocol(OpenverseProtocol.get());

    @Getter
    private ResourceManager resourceManager = new ResourceManager();

    public OpenverseClient(@NonNull Client client) {
        this.client = client;
        client.getConnection().setDefaultChannel(channel);

        universe = new Universe<>();
    }

    public void setup() {
        client.getConnection().send(channel, new GetUniversePacket());
        channel.getEventManager().register(UniverseInfoPacket.class, (conn, pkt) -> {
            // ensures that the universe is empty
            universe.clear();

            // foreach name retrieved creates an instance of a client world
            for (String wn : pkt.getWorldNames());
                // todo universe.add(new ClientWorld(wn, 1));
        });
    }

    @Override
    public List<?> getPlayers() {
        return singletonList(getPlayer());
    }

    @Override
    public Client getEndpoint() {
        return client;
    }
}
