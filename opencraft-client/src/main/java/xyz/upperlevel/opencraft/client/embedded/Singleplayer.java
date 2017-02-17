package xyz.upperlevel.opencraft.client.embedded;

import xyz.upperlevel.opencraft.server.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.server.world.World;
import xyz.upperlevel.utils.packet.listener.PacketReceiveListener;
import xyz.upperlevel.utils.packet.listener.PacketReceiveListenerManager;

public class Singleplayer {

    public static final EmbeddedServer connection = new EmbeddedServer();

    private static final World world = new World("mh");

    static {
        PacketReceiveListenerManager listenerManager = connection.getListenerManager();
        listenerManager.registerListener(new PacketReceiveListener<PlayerTeleportPacket>() {
            @Override
            public Class<PlayerTeleportPacket> getPacketClass() {
                return PlayerTeleportPacket.class;
            }

            @Override
            public void onReceive(PlayerTeleportPacket packet) {
                world.getPlayer().teleport(
                        packet.getX(),
                        packet.getY(),
                        packet.getZ(),
                        packet.getYaw(),
                        packet.getPitch()
                );
            }
        });
    }

    public static EmbeddedServer connection() {
        return connection;
    }

    private Singleplayer() {
    }
}
