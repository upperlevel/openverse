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
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    @Override
    public void register(String id, BlockType entry) {
        super.register(id, entry);
        registerId(entry);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getConnection().send(Openverse.channel(), new BlockRegistryPacket(getOrderedEntries()));
    }
}
