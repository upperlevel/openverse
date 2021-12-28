package xyz.upperlevel.openverse.server.resource;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.EventPriority;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.item.ItemTypeRegistry;
import xyz.upperlevel.openverse.network.world.registry.ItemRegistryPacket;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

public class ServerItemTypeRegistry extends ItemTypeRegistry implements Listener {

    public ServerItemTypeRegistry(OpenverseServer server, BlockTypeRegistry blockTypes) {
        super(server, blockTypes);

        server.getEventManager().register(this);
    }

    @Override
    public void register(String id, ItemType entry) {
        super.register(id, entry);
        registerId(entry);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getConnection().send(getModule().getChannel(), new ItemRegistryPacket(getOrderedEntries()));
    }
}
