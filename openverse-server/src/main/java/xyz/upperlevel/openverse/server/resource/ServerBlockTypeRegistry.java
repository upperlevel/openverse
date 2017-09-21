package xyz.upperlevel.openverse.server.resource;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.BlockRegistryPacket;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.world.block.BlockTypeRegistry;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.event.PlayerJoinEvent;

import java.io.File;
import java.util.logging.Logger;

public class ServerBlockTypeRegistry extends BlockTypeRegistry implements Listener {
    /**
     * If true auto-send the BlockRegistryPacket signaling what blocks were registered<br>
     * DO NOT CHANGE THIS (Unless you know what you're doing)
     */
    @Getter
    @Setter
    private boolean autoSend = true;

    public ServerBlockTypeRegistry() {
        Openverse.getEventManager().register(this);
    }

    public void register(String id, BlockType entry) {
        super.register(id, entry);
        if (autoSend) {//If autoSend enabled
            //Send the blockRegistry packet that registers this block
            BlockRegistryPacket packet = new BlockRegistryPacket(new String[]{entry.getId()});
            OpenverseServer.get().getEndpoint().getConnections().forEach(s -> s.send(Openverse.channel(), packet));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getConnection().send(Openverse.channel(), new BlockRegistryPacket(entries()));
    }
}
