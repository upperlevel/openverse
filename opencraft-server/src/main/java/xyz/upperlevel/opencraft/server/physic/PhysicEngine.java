package xyz.upperlevel.opencraft.server.physic;

import xyz.upperlevel.opencraft.server.entity.Entity;
import xyz.upperlevel.opencraft.server.physic.collision.Box;
import xyz.upperlevel.opencraft.server.world.World;

import java.util.HashSet;
import java.util.Set;

public class PhysicEngine {

    private Set<Force> forces = new HashSet<>();

    public PhysicEngine() {
    }

    public void update(Entity entity, World world, long delta) {
        // todo
        forces.forEach(f -> f.apply(entity, delta));

        Box eb = entity.getType().getShape().getBox();
    }
}
