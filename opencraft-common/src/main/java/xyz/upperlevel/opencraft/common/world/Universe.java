package xyz.upperlevel.opencraft.common.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

public class Universe {

    @Getter
    @Setter
    @NonNull
    private String name;

    private Map<String, World> worlds = new HashMap<>();

    public Universe(String name) {
        Objects.requireNonNull(name, "name");
        this.name = name;
    }

    public Universe addWorld(World world) {
        Objects.requireNonNull(world, "world");
        worlds.put(world.getName(), world);
        return this;
    }

    public World getWorld(String worldName) {
        return worlds.get(worldName);
    }

    public World removeWorld(String worldName) {
        return worlds.remove(worldName);
    }

    public boolean removeWorld(World world) {
        return worlds.remove(world.getName()) != null;
    }

    public Collection<World> getWorlds() {
        return worlds.values();
    }
}