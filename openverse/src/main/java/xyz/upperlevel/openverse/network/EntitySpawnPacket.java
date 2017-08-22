package xyz.upperlevel.openverse.network;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.resource.EntityType;

/**
 * This packet is sent from the server to the client to spawn an entity.
 */
@Getter
@RequiredArgsConstructor
public class EntitySpawnPacket implements Packet {
    private final int entityId;
    private final EntityType entityType;
}
