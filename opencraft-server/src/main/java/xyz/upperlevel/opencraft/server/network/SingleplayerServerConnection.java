package xyz.upperlevel.opencraft.server.network;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.opencraft.client.network.SingleplayerClientConnection;
import xyz.upperlevel.utils.packet.Connection;
import xyz.upperlevel.utils.packet.listener.PacketListenerManager;
import xyz.upperlevel.utils.packet.packet.Packet;

import java.util.Optional;

public class SingleplayerServerConnection implements Connection {

    public static final SingleplayerServerConnection $ = new SingleplayerServerConnection();

    @Setter
    private PacketListenerManager listenerManager = new PacketListenerManager();

    public SingleplayerServerConnection() {
    }

    public Optional<PacketListenerManager> getListenerManager() {
        return Optional.ofNullable(listenerManager);
    }

    @Override
    public void sendPacket(Packet packet) {
        SingleplayerClientConnection.$()
                .getListenerManager()
                .ifPresent(listenerManager -> listenerManager.onReceive(packet));
    }

    public static SingleplayerServerConnection $() {
        return $;
    }
}
