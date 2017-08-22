package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;

/**
 * This packet is sent from the server to the client to remove an entity.
 */
@Getter
@RequiredArgsConstructor
public class EntityRemovePacket implements Packet {
    private final int entityId;
}
