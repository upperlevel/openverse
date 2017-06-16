package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.Universe;

import java.util.ArrayList;
import java.util.List;

public class UniversePacket implements Packet {

    @Getter
    private final List<String> worlds;

    public UniversePacket(@NonNull List<String> worlds) {
        this.worlds = worlds;
    }

    public UniversePacket(@NonNull Universe universe) {
        worlds = new ArrayList<>(universe.getWorldMap().keySet());
    }
}
