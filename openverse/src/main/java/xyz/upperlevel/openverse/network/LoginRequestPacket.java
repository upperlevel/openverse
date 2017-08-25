package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent when the client firstly login to the server or change its world.
 * todo It might contain an id to identify the player that is joining.
 * When received the server provides to spawn the player and responds back with a {@link LoginResponsePacket}.
 */
@NoArgsConstructor
public class LoginRequestPacket implements Packet {
    @Override
    public void toData(ByteBuf out) {
    }

    @Override
    public void fromData(ByteBuf in) {
    }
}