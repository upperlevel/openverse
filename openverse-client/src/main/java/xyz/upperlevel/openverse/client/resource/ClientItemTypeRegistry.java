package xyz.upperlevel.openverse.client.resource;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.item.ItemTypeRegistry;
import xyz.upperlevel.openverse.network.world.registry.ItemRegistryPacket;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

public class ClientItemTypeRegistry extends ItemTypeRegistry implements PacketListener {

    public ClientItemTypeRegistry(OpenverseClient client, BlockTypeRegistry blockTypes) {
        super(client, blockTypes);

        client.getChannel().register(this);
    }

    @PacketHandler
    public void onItemRegistry(Connection connection, ItemRegistryPacket packet) {
        for (String itemName : packet.getItems()) {
            ItemType type = super.entry(itemName);

            if (type != null) {
                super.registerId(type);
            } else {
                getModule().getLogger().severe("Illegal ItemRegistryPacket data: cannot find item \"" + itemName + "\"");
            }
        }
    }
}
