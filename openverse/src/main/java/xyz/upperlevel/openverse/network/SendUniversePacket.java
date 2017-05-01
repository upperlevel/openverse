package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.World;

import java.util.ArrayList;
import java.util.List;

public class SendUniversePacket implements Packet {

    @Getter
    private final String spawnWorld;

    @Getter
    private final double spawnX, spawnY, spawnZ;

    @Getter
    private final List<String> worlds;

    public SendUniversePacket(
            @NonNull String spawnWorld,
            double spawnX, double spawnY, double spawnZ,
            @NonNull List<String> worlds
            ) {
        this.spawnWorld = spawnWorld;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
        this.worlds = worlds;
    }

    public SendUniversePacket(@NonNull Universe<? extends World> universe) {
        Location spawn = universe.getSpawn();
        spawnWorld = spawn.world().getName();
        spawnX = spawn.x();
        spawnY = spawn.y();
        spawnZ = spawn.z();
        worlds = new ArrayList<>(universe.getWorldMap().keySet());
    }
}
