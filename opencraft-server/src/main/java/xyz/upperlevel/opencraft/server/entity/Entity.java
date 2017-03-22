package xyz.upperlevel.opencraft.server.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.server.math.Vector;
import xyz.upperlevel.opencraft.server.world.Location;

public abstract class Entity {

    @Getter
    @Setter
    @NonNull
    private Location loc;

    @Getter
    @Setter
    @NonNull
    private Vector vel;

    public Entity() {
    }

    public abstract void tick();
}
