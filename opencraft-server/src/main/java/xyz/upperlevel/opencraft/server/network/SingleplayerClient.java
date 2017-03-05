package xyz.upperlevel.opencraft.common.network;

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
