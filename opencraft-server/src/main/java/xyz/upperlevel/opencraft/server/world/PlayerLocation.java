package xyz.upperlevel.opencraft.server.world;

import lombok.Getter;
import xyz.upperlevel.opencraft.server.network.SingleplayerServer;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.ulge.util.math.AngleUtil;

import java.util.Objects;

public class PlayerLocation {

    @Getter
    private Player player;

    @Getter
    private double x, y, z;

    @Getter
    private float yaw, pitch;

    public PlayerLocation(Player player) {
        Objects.requireNonNull(player, "player");
        this.player = player;
    }

    public void sendPositionChanges() {
        SingleplayerServer.connection().sendPacket(new PlayerTeleportPacket(x, y, z, yaw, pitch));
    }

    public PlayerLocation setX(double x) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation setY(double y) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation setZ(double z) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation setYaw(float yaw) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation setPitch(float pitch) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation teleport(double x, double y, double z) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation teleport(float yaw, float pitch) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation teleport(double x, double y, double z, float yaw, float pitch) {
        teleport(x, y, z, yaw, pitch, true);
        return this;
    }

    public PlayerLocation teleport(double x, double y, double z, float yaw, float pitch, boolean sendPacket) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = (float) AngleUtil.normalizeDegreesAngle(yaw);
        this.pitch = (float) AngleUtil.normalizeDegreesAngle(pitch);
        if (sendPacket)
            sendPositionChanges();
        return this;
    }
}