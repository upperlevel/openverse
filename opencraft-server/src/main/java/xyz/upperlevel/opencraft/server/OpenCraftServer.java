package xyz.upperlevel.opencraft.server;

import lombok.Getter;
import xyz.upperlevel.opencraft.server.world.BlockType;
import xyz.upperlevel.opencraft.server.network.player.ServerListenerSubscriber;
import xyz.upperlevel.opencraft.server.world.*;

public class OpenCraftServer {

    public static final OpenCraftServer GET = new OpenCraftServer();

    @Getter
    private World world = new World(chunk -> {
            for (int x = 0; x < 16; x++)
                for (int z = 0; z < 16; z++)
                    chunk.setType(BlockType.create("grass_shape"), x, 0, z);
    });

    @Getter
    private Player player = new Player();

    public OpenCraftServer() {
        start();
    }

    public void start() {
        ServerListenerSubscriber.subscribe();
    }

    public static OpenCraftServer get() {
        return GET;
    }
}