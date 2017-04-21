package xyz.upperlevel.openverse.world;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is something similar to the World manager, in the sense that it stores all the worlds, their ids and the
 * world that the player is currently in
 */
public class Universe {

    private Map<String, World> worlds = new HashMap<>();

    @Getter
    private World current;

    public Universe(World initial) {
        this.current = initial;
        worlds.put(initial.getName(), initial);
    }

    public Map<String, World> getWorldMap() {
        return Collections.unmodifiableMap(worlds);
    }

    public Collection<World> get() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public World get(String name) {
        return worlds.get(name);
    }

    public boolean add(World world) {
        return worlds.putIfAbsent(world.getName(), world) == null;
    }

    public boolean remove(World world) {
        return worlds.remove(world.getName()) != null;
    }

    public boolean remove(String name) {
        return worlds.remove(name) != null;
    }

    public void move(World world) {
        assert worlds.containsKey(world.getName());
        this.current = world;
    }
}
