package xyz.upperlevel.openverse.client.render.world;

import lombok.Getter;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.world.event.WorldCreateEvent;
import xyz.upperlevel.openverse.world.event.WorldDeleteEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RenderUniverse implements Listener {

    @Getter
    private final OpenverseClient client;

    private final Map<String, RenderWorld> worldsByName = new HashMap<>();

    // listens for events by client universe

    public RenderUniverse(OpenverseClient client) {
        this.client = client;
        client.getEventManager().register(this);
    }

    public RenderWorld getWorld(String name) {
        return worldsByName.get(name);
    }

    public Collection<RenderWorld> getWorlds() {
        return worldsByName.values();
    }

    @EventHandler
    public void onWorldCreate(WorldCreateEvent event) {
        String name = event.getWorld().getName();
        worldsByName.put(name, new RenderWorld(client, name));
    }

    @EventHandler
    public void onWorldDelete(WorldDeleteEvent event) {
        worldsByName.remove(event.getWorld().getName());
    }
}
