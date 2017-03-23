package xyz.upperlevel.opencraft.server.physic;

import lombok.Getter;
import xyz.upperlevel.opencraft.server.entity.Entity;
import xyz.upperlevel.opencraft.server.world.World;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    @Getter
    private List<Force> forces = new ArrayList<>();

    public PhysicEngine() {
    }

    public void update(Entity entity, World world, long delta) {
        forces.forEach(f -> f.update(entity, delta));


    }
}
