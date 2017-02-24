package xyz.upperlevel.opencraft.common.network;

import xyz.upperlevel.utils.packet.packet.Packet;

public final class SingleplayerServer {

    public static final SingleplayerNetworkConnection connection = new SingleplayerNetworkConnection() {
        @Override
        public void sendPacket(Packet packet) {
            SingleplayerClient.connection().getListenerManager().ifPresent(manager -> manager.call(packet));
        }
    };

    private SingleplayerServer() {
    }

    public static SingleplayerNetworkConnection connection() {
        return connection;
    }
}