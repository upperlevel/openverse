package xyz.upperlevel.openverse.client.render;

import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.world.event.WorldCreateEvent;
import xyz.upperlevel.openverse.world.event.WorldDeleteEvent;

import java.nio.IntBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RenderUniverse implements Listener {

    private final Map<String, RenderWorld> worldsByName = new HashMap<>();

    // listens for events by client universe
    public RenderUniverse() {
        OpenverseClient.get().getEventManager().register(this);
    }

    public void addWorld(RenderWorld world) {
        worldsByName.put(world.getName(), world);
    }

    public boolean removeWorld(String name) {
        return worldsByName.remove(name) != null;
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
        addWorld(new RenderWorld(name));
    }

    @EventHandler
    public void onWorldDelete(WorldDeleteEvent event) {
        removeWorld(event.getWorld().getName());
    }
}
