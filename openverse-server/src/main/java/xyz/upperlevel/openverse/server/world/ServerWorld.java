package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.chunk.ChunkLocation;
import xyz.upperlevel.openverse.world.entity.Player;

public class ServerWorld extends World {

    public ServerWorld(String name) {
        super(name);
    }

    //the same as ClientWorld::setCenter
    public void onChangeChunk(ChunkLocation oldLoc, ChunkLocation newLoc, Player player) {
    }
}
