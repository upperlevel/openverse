package xyz.upperlevel.openverse.network.world.entity;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent from both endpoints when a player changes his position.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerChangePositionPacket implements Packet {
    private double x, y, z;

    @Override
    public void toData(ByteBuf out) {
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
    }
}
