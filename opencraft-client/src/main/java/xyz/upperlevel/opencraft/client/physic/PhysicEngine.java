package xyz.upperlevel.opencraft.client.physic;

import com.sun.istack.internal.Nullable;
import lombok.NonNull;
import org.joml.Vector3d;
import xyz.upperlevel.opencraft.client.physic.impl.GravityModifier;
import xyz.upperlevel.opencraft.client.physic.util.PhysicalBlock;
import xyz.upperlevel.opencraft.client.physic.util.PhysicalFace;
import xyz.upperlevel.opencraft.client.physic.util.RaycastIterator;
import xyz.upperlevel.opencraft.client.physic.util.RaycastUtil;
import xyz.upperlevel.opencraft.client.render.LocalBlock;
import xyz.upperlevel.opencraft.client.render.LocalWorld;

import java.util.LinkedList;
import java.util.List;

import static xyz.upperlevel.opencraft.client.physic.util.PhysicalFace.*;

public class PhysicEngine {

    public static final PhysicEngine in = new PhysicEngine();

    static {
        in.addModifier(new GravityModifier());
    }

    private List<SpeedModifier> modifiers = new LinkedList<>();

    public PhysicEngine() {
    }

    public void addModifier(@NonNull SpeedModifier modifier) {
        modifiers.add(modifier);
    }

    public void addModifier(int i, @Nullable SpeedModifier modifier) {
        modifiers.add(i, modifier);
    }

    public void removeModifier(SpeedModifier modifier) {
        modifiers.remove(modifier);
    }

    public void update(PhysicalViewer viewer, LocalWorld world) {
        modifiers.forEach(m -> m.update(viewer));

        float sx = viewer.x;
        float sy = viewer.y;
        float sz = viewer.z;

        float ex = viewer.x + viewer.getSpeedX();
        float ey = viewer.y + viewer.getSpeedY();
        float ez = viewer.z + viewer.getSpeedZ();

        // checks entity's grid intersections between start point and end point
        RaycastIterator ri = new RaycastIterator(sx, sy, sz, ex, ey, ez);

        while (ri.hasNext()) {
            PhysicalBlock rb = ri.next();

            int bx = rb.getX();
            int by = rb.getY();
            int bz = rb.getZ();
            PhysicalFace face = rb.getFace();

            LocalBlock lb = world.getBlock(bx, by, bz);

            // if the block is solid
            if (lb != null && lb.getShape() != null && !lb.getShape().isEmpty()) {

                // if the face colliding with is not null
                if (face == null)
                    continue;

                // gets colliding point
                Vector3d it = RaycastUtil.getIntersection(
                        sx, sy, sz,
                        ex, ey, ez,
                        bx, by, bz,
                        face
                );

                // sets player position (solid block found case)
                viewer.setPosition(
                        (float) ((face == RIGHT && viewer.getSpeedX() < 0 || face == LEFT && viewer.getSpeedX() >= 0) ? it.x : ex),
                        (float) ((face == UP && viewer.getSpeedY() < 0 || face == DOWN && viewer.getSpeedY() >= 0) ? it.y : ey),
                        (float) ((face == FRONT && viewer.getSpeedZ() < 0 || face == BACK && viewer.getSpeedZ() >= 0) ? it.z : ez)
                );

                /*
                // applies bounce
                viewer.setSpeed(
                       -viewer.getSpeedX() * 0.1f,
                       -viewer.getSpeedY() * 0.1f,
                       -viewer.getSpeedZ() * 0.1f
                );
                */
                viewer.setColliding(face, true);

                return;
            }
        }
        // sets the player position (no solid block found case)
        viewer.setPosition(ex, ey, ez);
    }
}
