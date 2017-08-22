package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent when a {@link LoginRequestPacket} is received (client join server or player change world).
 * It sends to the client the player's entity id.
 */
@Getter
@RequiredArgsConstructor
public class LoginResponsePacket implements Packet {
    private final int playerId;
}