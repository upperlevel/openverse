package xyz.upperlevel.opencraft.physic;

import xyz.upperlevel.opencraft.world.entity.Entity;

public interface Force {

    void apply(Entity entity, long delta);
}
