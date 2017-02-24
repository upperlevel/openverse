package xyz.upperlevel.opencraft.server;

import xyz.upperlevel.opencraft.common.network.packet.AskChunkPacket;
import xyz.upperlevel.opencraft.common.network.packet.ChunkPacket;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.common.network.SingleplayerServer;
import xyz.upperlevel.opencraft.server.world.Player;
import xyz.upperlevel.opencraft.server.world.World;
import xyz.upperlevel.utils.event.EventListener;

public final class Server {

    public static final Player player = new Player();

    public static final World world = new World("default-world");

    static {
        SingleplayerServer.connection().getListenerManager().ifPresent(manager -> {
            manager.register(PlayerTeleportPacket.class, new EventListener<PlayerTeleportPacket>() {
                @Override
                public byte getPriority() {
                    return 0;
                }

                @Override
                public void call(PlayerTeleportPacket event) {
                    player.getLocation().teleport(
                            event.getX(),
                            event.getY(),
                            event.getZ(),
                            event.getYaw(),
                            event.getPitch(),
                            false
                    );
                }
            });
            manager.register(AskChunkPacket.class, new EventListener<AskChunkPacket>() {
                @Override
                public byte getPriority() {
                    return 0;
                }

                @Override
                public void call(AskChunkPacket event) {
                    System.out.println("Server->Singleplayer client is asking packet at: x=" + event.getX() + " y=" + event.getY() + " z=" + event.getZ());
                    SingleplayerServer.connection().sendPacket(new ChunkPacket());
                }
            });
        });
    }

    private Server() {
    }
}
