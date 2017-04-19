package xyz.upperlevel.openverse.physic;

import xyz.upperlevel.openverse.world.entity.Entity;

public interface Force {

    void apply(Entity entity, long delta);
}
