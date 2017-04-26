package xyz.upperlevel.openverse.client.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.EntityTeleportPacket;

import java.util.function.BiConsumer;

public class EntityTeleportPacketReceiveListener implements BiConsumer<Connection, EntityTeleportPacket> {

    @Getter
    private final OpenverseClient main;

    public EntityTeleportPacketReceiveListener(@NonNull OpenverseClient main) {
        this.main = main;
    }

    @Override
    public void accept(Connection conn, EntityTeleportPacket pkt) {
        main.getPlayer().setPosition(
                // todo set world
                pkt.getX(),
                pkt.getY(),
                pkt.getZ(),
                pkt.getYaw(),
                pkt.getPitch()
        );
    }
}
