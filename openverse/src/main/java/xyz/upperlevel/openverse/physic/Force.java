package xyz.upperlevel.openverse.physic;

import xyz.upperlevel.openverse.world.entity.BaseEntity;

public interface Force {

    void apply(BaseEntity entity, long delta);
}
