package xyz.upperlevel.opencraft.client.embedded;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.controller.ControllableEntity;
import xyz.upperlevel.opencraft.server.network.packet.PlayerTeleportPacket;

public class WorldViewer implements ControllableEntity {

    @Getter
    private double x, y, z;

    @Getter
    private float yaw, pitch;

    public WorldViewer() {
    }

    private void updateAbsolutePosition() {
        Singleplayer.connection().sendPacket(new PlayerTeleportPacket(x, y, z, yaw, pitch));
    }

    @Override
    public void teleport(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        updateAbsolutePosition();
    }

    @Override
    public void teleport(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw   = yaw;
        this.pitch = pitch;
        updateAbsolutePosition();
    }

    @Override
    public void teleport(float yaw, float pitch) {
        this.yaw   = yaw;
        this.pitch = pitch;
        updateAbsolutePosition();
    }
}
