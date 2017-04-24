package xyz.upperlevel.openverse.network;

import lombok.Getter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.Universe;
import xyz.upperlevel.openverse.world.World;

import java.util.ArrayList;
import java.util.List;

public class UniverseInfoPacket implements Packet {

    @Getter
    private final List<String> worldNames;

    public UniverseInfoPacket(List<String> worldNames) {
        this.worldNames = worldNames;
    }

    public UniverseInfoPacket(Universe<? extends World> universe) {
        worldNames = new ArrayList<>(universe.getWorldMap().keySet());
    }
}
