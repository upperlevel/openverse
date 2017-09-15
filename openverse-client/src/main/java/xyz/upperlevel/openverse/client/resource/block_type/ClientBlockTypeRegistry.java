package xyz.upperlevel.openverse.client.resource.block_type;

import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.BlockRegistryPacket;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;

import java.io.File;
import java.util.logging.Logger;

public class ClientBlockTypeRegistry extends BlockTypeRegistry implements PacketListener {
    public ClientBlockTypeRegistry(File folder, Logger logger) {
        super(folder, logger);
        Openverse.channel().register(this);
    }

    @PacketHandler
    public void onBlockRegistryPacket(Connection conn, BlockRegistryPacket packet) {
        for(String rawType : packet.getBlocks()) {
            BlockType type = entry(rawType);
            if (type != null) {
                registerId(type);
            } else {
                Openverse.logger().warning("Block registry received for unknown block, id:" + rawType);
            }
        }
    }
}
