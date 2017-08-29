package xyz.upperlevel.openverse.network.world;

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
    private float x, y, z;

    @Override
    public void toData(ByteBuf out) {
        out.writeFloat(x);
        out.writeFloat(y);
        out.writeFloat(z);
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readFloat();
        y = in.readFloat();
        z = in.readFloat();
    }
}
