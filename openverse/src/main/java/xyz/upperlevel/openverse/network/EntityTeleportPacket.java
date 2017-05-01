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
    private long id;

    @Getter
    private String worldName;

    @Getter
    private double x, y, z;

    @Getter
    private double yaw, pitch;

    public EntityTeleportPacket(
            long id,
            @NonNull String worldName,
            double x, double y, double z,
            double yaw, double pitch
    ) {
        this.id = id;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public EntityTeleportPacket(long id, @NonNull Location to) {
        this.id = id;
        worldName = to.getWorld().getName();
        x = to.getX();
        y = to.getY();
        z = to.getZ();
        yaw = to.getYaw();
        pitch = to.getPitch();
    }
}
