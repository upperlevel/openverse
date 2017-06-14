package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.resource.BlockType;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.world.Universe;

public class ServerUniverse extends Universe<ServerWorld> implements Listener {

    @Getter
    private final OpenverseServer handle;

    public ServerUniverse(@NonNull OpenverseServer handle) {
        this.handle = handle;
        handle.getChannel().getEventManager().register(this);
    }

    public void load() {
        ServerWorld world = new ServerWorld("hello");
        addWorld(world);
        world.getBlock(0, 0, 0).setType(new BlockType("drug", true));
    }
}
