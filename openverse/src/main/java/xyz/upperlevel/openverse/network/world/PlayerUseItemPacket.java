package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.block.BlockFace;

/**
 * Called whenever a player uses an item (specifically the item in their hand)
 */
@AllArgsConstructor
@NoArgsConstructor
public class PlayerUseItemPacket implements Packet {
    @Getter
    @Setter
    private int x, y, z;

    @Getter
    @Setter
    private BlockFace face;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeByte(face.getId());
    }

    @Override
    public void fromData(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        face = BlockFace.fromId(in.readByte());
    }
}
