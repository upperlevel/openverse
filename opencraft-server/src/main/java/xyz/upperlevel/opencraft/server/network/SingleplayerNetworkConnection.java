package xyz.upperlevel.opencraft.server.network;

import lombok.Setter;
import xyz.upperlevel.utils.event.EventManager;
import xyz.upperlevel.utils.event.impl.SimpleEventManager;
import xyz.upperlevel.utils.packet.Connection;
import xyz.upperlevel.utils.packet.packet.Packet;

import java.util.Optional;

public abstract class SingleplayerNetworkConnection implements Connection {

    @Setter
    private EventManager listenerManager = new SimpleEventManager();

    public Optional<EventManager> getListenerManager() {
        return Optional.ofNullable(listenerManager);
    }
}
