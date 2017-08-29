package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent from both endpoints when a player changes his look.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerChangeLookPacket implements Packet {
    private float yaw, pitch;

    @Override
    public void toData(ByteBuf byteBuf) {
        byteBuf.writeFloat(yaw);
        byteBuf.writeFloat(pitch);
    }

    @Override
    public void fromData(ByteBuf byteBuf) {
        yaw = byteBuf.readFloat();
        pitch = byteBuf.readFloat();
    }
}
