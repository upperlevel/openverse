package xyz.upperlevel.openverse.server.world;

import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

import java.util.*;

/**
 * This is something similar to the World manager, in the sense that it stores all the worlds and their ids.
 */
public class Universe {
    // the spawn location is just server side
    private Location spawn;
    private final Map<String, ServerWorld> worlds = new HashMap<>();

    public Location getSpawn() {
        if (spawn == null) {
            ServerWorld w = new ServerWorld("world");
            addWorld(w);
            spawn = new Location(w, 0, 50, 0);
        }
        return spawn;
    }

    public World getWorld(String name) {
        return worlds.get(name);
    }

    public boolean addWorld(ServerWorld world) {
        worlds.put(world.getName().toLowerCase(Locale.ENGLISH), world);
        return false;
    }

    public boolean removeWorld(ServerWorld world) {
        return removeWorld(world.getName());
    }

    public boolean removeWorld(String name) {
        return worlds.remove(name.toLowerCase(Locale.ENGLISH)) != null;
    }

    public Collection<World> getWorlds() {
        return Collections.unmodifiableCollection(worlds.values());
    }

    public void onTick() {
        for (ServerWorld  world : worlds.values()) {
            world.onTick();
        }
    }
}
