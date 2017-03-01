package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.client.OpenCraftClient;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
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
        OpenCraftClient.get().getViewer().teleport(
                packet.getX(),
                packet.getY(),
                packet.getZ(),
                packet.getYaw(),
                packet.getPitch(),
                false
        );
    }
}
