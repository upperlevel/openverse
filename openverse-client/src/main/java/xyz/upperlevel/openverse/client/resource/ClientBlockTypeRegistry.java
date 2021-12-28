package xyz.upperlevel.openverse.client.resource;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.world.registry.BlockRegistryPacket;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

public class ClientBlockTypeRegistry extends BlockTypeRegistry implements PacketListener {

    public ClientBlockTypeRegistry(OpenverseClient client) {
        super(client);

        client.getChannel().register(this);
    }

    @PacketHandler
    public void onBlockRegistry(Connection connection, BlockRegistryPacket packet) {
        for (String blockName : packet.getBlocks()) {
            BlockType type = super.entry(blockName);

            if (type != null) {
                super.registerId(type);
            } else {
                OpenverseClient.get().getLogger().severe("Illegal BlockRegistryPacket data: cannot find block \"" + blockName + "\"");
            }
        }
    }
}
