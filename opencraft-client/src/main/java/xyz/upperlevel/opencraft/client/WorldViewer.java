package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.controller.ControllableEntity;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.server.network.SingleplayerServer;

public class WorldViewer implements ControllableEntity {

    @Getter
    private double x, y, z;

    @Getter
    private float yaw, pitch;

    public WorldViewer() {
    }

    public void sendPositionChanges() {
        SingleplayerServer.connection().sendPacket(new PlayerTeleportPacket(x, y, z, yaw, pitch));
    }

    @Override
    public void setX(double x) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setY(double y) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setZ(double z) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setYaw(float yaw) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void setPitch(float pitch) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void teleport(float yaw, float pitch) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void teleport(double x, double y, double z) {
        teleport(x, y, z, yaw, pitch, true);
    }

    @Override
    public void teleport(double x, double y, double z, float yaw, float pitch) {
        teleport(x, y, z, yaw, pitch, true);
    }

    public void teleport(double x, double y, double z, float yaw, float pitch, boolean sendPacket) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        if (sendPacket)
            sendPositionChanges();
    }
}
