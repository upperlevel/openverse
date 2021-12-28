package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

import java.util.*;

/**
 * A world manager, it handles worlds and their IDs.
 */
public class Universe implements PacketListener {
    @Getter
    private final OpenverseServer server;

    // the spawn location is just server side
    private Location spawn;
    private final Map<String, ServerWorld> worlds = new HashMap<>();

    public Universe(OpenverseServer server) {
        this.server = server;
    }

    public Location getSpawn() {
        if (spawn == null) {
            ServerWorld w = new ServerWorld(server, "world");
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
