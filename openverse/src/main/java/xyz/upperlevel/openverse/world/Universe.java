package xyz.upperlevel.openverse.world;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.world.event.WorldCreateEvent;
import xyz.upperlevel.openverse.world.event.WorldDeleteEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is something similar to the World manager, in the sense that it stores all the worlds and their ids.
 */
public class Universe<W extends World> {

    // the spawn location is just server side

    private final Map<String, W> worlds = new HashMap<>();

    public W getWorld(String name) {
        return worlds.get(name);
    }

    public boolean addWorld(W world) {
        if (worlds.putIfAbsent(world.getName(), world) == null) {
            Openverse.getProxy().getEventManager().call(new WorldCreateEvent(world));
            return true;
        }
        return false;
    }

    public boolean removeWorld(W world) {
        return removeWorld(world.getName());
    }

    public boolean removeWorld(String name) {
        World w =  worlds.remove(name);
        if (w != null) {
            Openverse.getProxy().getEventManager().call(new WorldDeleteEvent(w));
            return true;
        }
        return false;
    }

    public Map<String, W> getWorldMap() {
        return Collections.unmodifiableMap(worlds);
    }

    public Collection<W> getWorlds() {
        return Collections.unmodifiableCollection(worlds.values());
    }
}
