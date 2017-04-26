package xyz.upperlevel.openverse.client.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.world.ClientWorld;
import xyz.upperlevel.openverse.network.UniverseInfoPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.Universe;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UniverseInfoPacketReceiveListener implements BiConsumer<Connection, UniverseInfoPacket> {

    @Getter
    private final OpenverseClient main;

    public UniverseInfoPacketReceiveListener(@NonNull OpenverseClient main) {
        this.main = main;
    }

    @Override
    public void accept(Connection conn, UniverseInfoPacket pkt) {
        Universe<ClientWorld> universe = main.getUniverse();

        // worlds
        universe.clear();
        for (String name : pkt.getWorlds())
            universe.add(new ClientWorld(name, 1));

        // spawn
        ClientWorld spawnWorld = universe.get(pkt.getSpawnWorld());
        Objects.requireNonNull(spawnWorld, "spawnWorld");

        universe.setSpawn(new Location(
                spawnWorld,
                pkt.getSpawnX(),
                pkt.getSpawnY(),
                pkt.getSpawnZ()
        ));
    }
}