package xyz.upperlevel.openverse.network.world.entity;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.network.SerialUtil;

import static xyz.upperlevel.openverse.network.SerialUtil.readString;
import static xyz.upperlevel.openverse.network.SerialUtil.writeString;

/**
 * This packet is sent from the server to the client and updates entity movements.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntityTeleportPacket implements Packet {
    private int entityId;
    private String worldName;
    private double x, y, z;
    private double yaw, pitch;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(entityId);
        writeString(worldName, out);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(yaw);
        out.writeDouble(pitch);
    }

    @Override
    public void fromData(ByteBuf in) {
        entityId = in.readInt();
        worldName = readString(in);
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        yaw = in.readDouble();
        pitch = in.readDouble();
    }
}
