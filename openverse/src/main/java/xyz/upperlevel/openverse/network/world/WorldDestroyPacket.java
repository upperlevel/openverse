package xyz.upperlevel.openverse.network.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This packet is used to destroy worlds in client side.
 * Is sent from the server to the client and when the client receives it
 * deletes the world instance for the given name it has.
 */
@Getter
@RequiredArgsConstructor
public class WorldDestroyPacket {
    private final String worldName;
}
