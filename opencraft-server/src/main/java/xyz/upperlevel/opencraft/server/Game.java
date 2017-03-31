package xyz.upperlevel.opencraft.server;

import lombok.Getter;
import xyz.upperlevel.opencraft.world.World;
import xyz.upperlevel.opencraft.server.world.ServerWorld;

public class Game {

    @Getter
    private World world = new ServerWorld(chunk -> chunk.getBlock(0, 0, 0).setType(null));

    public Game() {
    }
}