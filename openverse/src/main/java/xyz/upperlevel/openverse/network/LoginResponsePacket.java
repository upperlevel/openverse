package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent when a {@link LoginRequestPacket} is received (client join server or player change world).
 * It sends to the client the player's entity id.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponsePacket implements Packet {
    private int playerId;

    @Override
    public void toData(ByteBuf out) {
        out.writeInt(playerId);
    }

    @Override
    public void fromData(ByteBuf in) {
        playerId = in.readInt();
    }
}