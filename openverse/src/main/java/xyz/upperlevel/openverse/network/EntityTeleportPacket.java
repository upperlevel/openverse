package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.Location;

/**
 * At the moment the EntityTeleportPacket teleports always the only player the world has.
 * We have no entity system at the moment.
 */
public class EntityTeleportPacket implements Packet {

    @Getter
    private String worldName;

    @Getter
    private double x, y, z;

    @Getter
    private double yaw, pitch;

    public EntityTeleportPacket(
            @NonNull String worldName,
            double x, double y, double z,
            double yaw, double pitch
    ) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public EntityTeleportPacket(int id, Location to) {
        worldName = to.world().getName();
        x = to.x();
        y = to.y();
        z = to.z();
        yaw = to.yaw();
        pitch = to.pitch();
    }
}
