package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerBreakBlockPacket implements Packet {
    @Getter
    @Setter
    private int x, y, z;


    @Override
    public void toData(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }
}
