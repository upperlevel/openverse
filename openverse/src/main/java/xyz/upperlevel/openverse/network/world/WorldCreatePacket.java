package xyz.upperlevel.openverse.network.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This packet contains world name. It's sent from the server to the client.
 * When the client receives it, creates a new world with the name contained.
 */
@Getter
@RequiredArgsConstructor
public class WorldCreatePacket {
    private final String worldName;
}
