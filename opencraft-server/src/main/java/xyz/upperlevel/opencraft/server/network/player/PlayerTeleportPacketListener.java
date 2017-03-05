package xyz.upperlevel.opencraft.server.network.player;

import xyz.upperlevel.opencraft.server.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.server.OpenCraftServer;
import xyz.upperlevel.utils.event.EventListener;

public class PlayerTeleportPacketListener extends EventListener<PlayerTeleportPacket> {

    public PlayerTeleportPacketListener() {
    }

    @Override
    public byte getPriority() {
        return 0;
    }

    @Override
    public void call(PlayerTeleportPacket packet) {
        OpenCraftServer.get().getPlayer().teleport(
                packet.getX(),
                packet.getY(),
                packet.getZ(),
                packet.getYaw(),
                packet.getPitch(),
                false
        );
        //System.out.println("Server> Player has been teleport");
    }
}
