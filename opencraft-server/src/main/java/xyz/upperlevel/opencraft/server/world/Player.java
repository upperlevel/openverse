package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.server.network.SingleplayerServer;
import xyz.upperlevel.opencraft.server.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.ulge.util.math.AngleUtil;

public class Player {

    @Getter
    private float x, y, z;

    @Getter
    private float yaw, pitch;

    public Player() {
    }

    public Player(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Player teleport(float x, float y, float z, float yaw, float pitch, boolean sendPacket) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = (float) AngleUtil.normalizeDegreesAngle(yaw);
        this.pitch = (float) AngleUtil.normalizeDegreesAngle(pitch);
        if (sendPacket)
            SingleplayerServer.connection().sendPacket(new PlayerTeleportPacket(this.x, this.y, this.z, this.yaw, this.pitch));
        return this;
    }
}