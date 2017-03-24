package xyz.upperlevel.opencraft.client.physic;

import lombok.Getter;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.physic.impl.Gravity;
import xyz.upperlevel.opencraft.client.render.LocalWorld;
import xyz.upperlevel.opencraft.server.entity.Entity;
import xyz.upperlevel.opencraft.server.world.Location;
import xyz.upperlevel.ulge.window.Window;

import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    public static final PhysicEngine in = new PhysicEngine();

    static {
        in.forces.add(new Gravity());
    }

    @Getter
    private List<Force> forces = new ArrayList<>();

    public PhysicEngine() {
    }

    public void update(Window w, Entity entity, LocalWorld world, long delta) {
        Vector3f vel = entity.getVel();
        float vx = vel.x;
        float vy = vel.y;
        float vz = vel.z;

        Location loc = entity.getLoc();
        float sx = loc.getX();
        float sy = loc.getY();
        float sz = loc.getZ();

        float ex = sx + vx;
        float ey = sy + vy;
        float ez = sz + vz;

        float dx, dy, dz;
        if (vx > 0) {
            dx = ex - sx;
        }
    }
}
