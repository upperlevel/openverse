package xyz.upperlevel.opencraft.client.network;

import xyz.upperlevel.opencraft.common.network.SingleplayerNetworkConnection;
import xyz.upperlevel.opencraft.server.network.SingleplayerServer;
import xyz.upperlevel.utils.packet.packet.Packet;

public final class SingleplayerClient {

    public static final SingleplayerNetworkConnection connection = new SingleplayerNetworkConnection() {
        @Override
        public void sendPacket(Packet packet) {
            SingleplayerServer.connection().getListenerManager().ifPresent(manager -> manager.call(packet));
        }
    };

    private SingleplayerClient() {
    }

    public static SingleplayerNetworkConnection connection() {
        return connection;
    }
}
