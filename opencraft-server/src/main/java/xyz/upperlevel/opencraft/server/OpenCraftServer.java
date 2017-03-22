package xyz.upperlevel.opencraft.server;

import lombok.Getter;
import xyz.upperlevel.opencraft.server.network.player.ServerListenerSubscriber;
import xyz.upperlevel.opencraft.server.world.BlockType;
import xyz.upperlevel.opencraft.server.world.Player;
import xyz.upperlevel.opencraft.server.world.World;

import java.util.Random;

public class OpenCraftServer {

    public static final OpenCraftServer GET = new OpenCraftServer();

    private int seed = new Random().nextInt();

    @Getter
    private World world = new World(chunk -> {
        // CHUNK GENERATION
        if (chunk.getZ() == 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setType(BlockType.create("grass_shape"), x, 0, z);
                }
            }

            if (chunk.getX() == 0 && chunk.getY() == 0)
                for (int y = 1; y < 16; y++) {
                    chunk.setType(BlockType.create("grass_shape"), 0, y, 0);
                }
        }
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