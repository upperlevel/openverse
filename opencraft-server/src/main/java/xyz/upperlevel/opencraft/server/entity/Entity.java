package xyz.upperlevel.opencraft.server.entity;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.opencraft.server.math.Vector;
import xyz.upperlevel.opencraft.server.world.Location;

public abstract class Entity {
    @Getter
    @Setter
    private Location loc;

    @Getter
    @Setter
    private Vector vel;

    public abstract void tick();
}
