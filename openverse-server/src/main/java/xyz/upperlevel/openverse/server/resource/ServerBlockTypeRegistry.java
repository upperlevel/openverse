package xyz.upperlevel.openverse.server.resource;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.network.world.registry.BlockRegistryPacket;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

public class ServerBlockTypeRegistry extends BlockTypeRegistry implements Listener {
    public ServerBlockTypeRegistry(OpenverseServer server) {
        super(server);

        server.getEventManager().register(this);
    }

    @Override
    public void register(String id, BlockType entry) {
        super.register(id, entry);
        registerId(entry);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getConnection().send(getModule().getChannel(), new BlockRegistryPacket(getOrderedEntries()));
    }
}
