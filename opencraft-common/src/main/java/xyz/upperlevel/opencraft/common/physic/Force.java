package xyz.upperlevel.opencraft.common.physic;

import xyz.upperlevel.opencraft.common.entity.Entity;

public interface Force {

    void apply(Entity entity, long delta);
}
