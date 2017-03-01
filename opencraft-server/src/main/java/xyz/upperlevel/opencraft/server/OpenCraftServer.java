package xyz.upperlevel.opencraft.server;

import lombok.Getter;
import xyz.upperlevel.opencraft.common.world.BridgeBlockType;
import xyz.upperlevel.opencraft.server.network.player.ServerListenerSubscriber;
import xyz.upperlevel.opencraft.server.world.*;

public class OpenCraftServer {

    public static final OpenCraftServer GET = new OpenCraftServer();

    @Getter
    private World world = new World(ChunkProvider.NULL, chunk -> {
        for (int x = 0; x < 16; x++)
            for (int y = 0; y < 16; y++)
                for (int z = 0; z < 16; z++)
                    chunk.setType(BridgeBlockType.create("test_id"), x, y, z);
        System.out.println("server> e invece...");
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