package xyz.upperlevel.openverse.world;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is something similar to the World manager, in the sense that it stores all the worlds and their ids.
 */
public class Universe<W extends World> {

    // the spawn location is just server side

    private Map<String, W> worlds = new HashMap<>();

    public Universe() {
    }

    public Map<String, W> getWorldMap() {
        return Collections.unmodifiableMap(worlds);
    }

    public Collection<W> get() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public W get(String name) {
        return worlds.get(name);
    }

    public boolean add(W world) {
        return worlds.putIfAbsent(world.getName(), world) == null;
    }

    public boolean remove(W world) {
        return worlds.remove(world.getName()) != null;
    }

    public boolean remove(String name) {
        return worlds.remove(name) != null;
    }

    /**
     * Clears all worlds.
     */
    public void clear() {
        worlds.clear();
    }
}
