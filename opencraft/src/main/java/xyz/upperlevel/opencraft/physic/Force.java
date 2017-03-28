package xyz.upperlevel.opencraft.physic;

import xyz.upperlevel.opencraft.entity.Entity;

public interface Force {

    void apply(Entity entity, long delta);
}
