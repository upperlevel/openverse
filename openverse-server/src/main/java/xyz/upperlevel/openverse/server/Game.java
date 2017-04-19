package xyz.upperlevel.openverse.server;

import lombok.Getter;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.server.world.ServerWorld;

public class Game {

    @Getter
    private World world = new ServerWorld(chunk -> chunk.getBlock(0, 0, 0).setType(null));

    public Game() {
    }
}