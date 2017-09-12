package xyz.upperlevel.openverse.server.resource;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.BlockRegistryPacket;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.resource.block.BlockType;
import xyz.upperlevel.openverse.resource.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;

import java.io.File;
import java.util.logging.Logger;

public class ServerBlockTypeRegistry extends BlockTypeRegistry implements Listener {
    /**
     * If true auto-send the BlockRegistryPacket signaling what blocks were registered<br>
     * DO NOT CHANGE THIS (Unless you know what you're doing)
     */
    private boolean autoSend = true;

    public ServerBlockTypeRegistry(File folder, Logger logger) {
        super(folder, logger);
        Openverse.getEventManager().register(this);
    }

    public void register(String id, BlockType entry) {
        super.register(id, entry);
        super.registerId(entry);//register rawId
        if (autoSend) {//If autoSend enabled
            //Send the blockRegistry packet that registers this block
            BlockRegistryPacket packet = new BlockRegistryPacket(new String[]{entry.getId()});
            OpenverseServer.get().getEndpoint().getConnections().forEach(s -> s.send(Openverse.channel(), packet));
        }
    }

    @Override
    public int loadFolder(ResourceLoader<BlockType> loader, File folder) {
        boolean send = autoSend;
        autoSend = false;
        try {
            int res = super.loadFolder(loader, folder);

            //If autoSend was enabled
            if (send && res > 0) {
                //Send to the clients every block registered in this folder
                int offset = getNextId() - res;
                String[] blocks = new String[res];
                for (int i = 0; i < res; i++) {
                    blocks[i] = entry(offset + i).getId();
                }
                BlockRegistryPacket packet = new BlockRegistryPacket(blocks);
                OpenverseServer.get().getEndpoint().getConnections().forEach(s -> s.send(Openverse.channel(), packet));
            }
            return res;
        } finally {
            autoSend = send;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getConnection().send(Openverse.channel(), new BlockRegistryPacket(entries()));
    }
}
