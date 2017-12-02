package xyz.upperlevel.openverse.network.world.entity;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.Entity;

/**
 * This packet is sent from the server to the client and updates entity movements.
 */
@Getter
public class EntityTeleportPacket implements Packet {
    private int entityId;
    private double x, y, z;
    private double yaw, pitch;

    public EntityTeleportPacket() {
    }

    public EntityTeleportPacket(int entityId, double x, double y, double z, double yaw, double pitch) {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public EntityTeleportPacket(int entityId, Location loc) {
        this(entityId, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public EntityTeleportPacket(Entity entity) {
        this(entity.getId(), entity.getLocation());
    }


    @Override
    public void toData(ByteBuf out) {
        out.writeInt(entityId);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(yaw);
        out.writeDouble(pitch);
    }

    @Override
    public void fromData(ByteBuf in) {
        entityId = in.readInt();
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        yaw = in.readDouble();
        pitch = in.readDouble();
    }
}
