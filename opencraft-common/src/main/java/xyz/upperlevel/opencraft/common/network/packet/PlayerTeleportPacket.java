package xyz.upperlevel.opencraft.common.network.packet;

import lombok.Getter;
import xyz.upperlevel.utils.packet.packet.Packet;

public class PlayerTeleportPacket implements Packet {

    @Getter
    private float x, y, z;

    @Getter
    private float yaw, pitch;

    public PlayerTeleportPacket(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}