package xyz.upperlevel.opencraft.server.physic;

import xyz.upperlevel.opencraft.server.entity.Entity;

public interface Force {

    void apply(Entity entity, long delta);
}
