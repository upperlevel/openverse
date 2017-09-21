package xyz.upperlevel.openverse.physic;

import org.joml.Vector3d;
import xyz.upperlevel.openverse.world.block.BlockType;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.Entity;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;

public class PhysicEngine {
    /* TODO
    protected Set<Force> globalForces = new HashSet<>();



    public void addGlobalForce(Force force) {
        globalForces.add(force);
    }

    public void removeGlobalForce(Force force) {
        globalForces.remove(force);
    }


    public void update(Entity entity, World world, long delta) {
        globalForces.forEach(f -> f.apply(entity, delta));

        Location loc = entity.getLocation();
        double sx = loc.getX();
        double sy = loc.getY();
        double sz = loc.getZ();

        Vector3d vel = entity.getVelocity();
        double vx = vel.x;
        double vy = vel.y;
        double vz = vel.z;

        double ex = sx + vx;
        double ey = sy + vy;
        double ez = sz + vz;

        int bx = (int) ex;
        int by = (int) ey;
        int bz = (int) ez;
        BlockType blockType = world.getBlock(bx, by, bz).getType();

        if (blockType != null && blockType.isSolid()) {
            Model s = blockType.getModel();

            // block box
            Box bb = s.getBox().copy();
            bb.add(bx, by, bz);

            // entity box
            Box eb = entity.getType().getModel().getBox().copy();
            eb.add(ex, ey, ez);

            // if collides
            if (bb.intersect(eb)) {
                double dx = bb.maxX - eb.minX;
                double tx = dx / (vx == 0 ? Double.POSITIVE_INFINITY : vx);

                double dy = bb.maxY - eb.minY;
                double ty = dy / (vy == 0 ? Double.POSITIVE_INFINITY : vy);

                double dz = bb.maxZ - eb.minZ;
                double tz = dz / (vz == 0 ? Double.POSITIVE_INFINITY : vz);

                double t = max(tx, max(ty, tz));

                loc.set((float) (vx * t), (float) (vy * t), (float) (vz * t));
                return;
            }
            loc.set(vx, vy, vz);
        }
    }
    */
}