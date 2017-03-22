package xyz.upperlevel.opencraft.client.physic;

import com.sun.istack.internal.Nullable;
import lombok.NonNull;
import org.joml.Vector3d;
import org.joml.Vector3f;
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

    private List<PhysicModifier> modifiers = new LinkedList<>();

    public PhysicEngine() {
    }

    public void addModifier(@NonNull PhysicModifier modifier) {
        modifiers.add(modifier);
    }

    public void addModifier(int i, @Nullable PhysicModifier modifier) {
        modifiers.add(i, modifier);
    }

    public void removeModifier(PhysicModifier modifier) {
        modifiers.remove(modifier);
    }

    public void update(PhysicalViewer viewer, LocalWorld world) {
        modifiers.forEach(m -> m.update(viewer));

        float sx = viewer.x;
        float sy = viewer.y;
        float sz = viewer.z;

        float ex = viewer.x + viewer.speedX;
        float ey = viewer.y + viewer.speedY;
        float ez = viewer.z + viewer.speedZ;

        // check player's grid intersections between start point and end point
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
                        (float) ((face == RIGHT && viewer.speedX < 0 || face == LEFT && viewer.speedX >= 0) ? it.x : ex),
                        (float) ((face == UP && viewer.speedY < 0 || face == DOWN && viewer.speedY >= 0) ? it.y : ey),
                        (float) ((face == FRONT && viewer.speedZ < 0 || face == BACK && viewer.speedZ >= 0) ? it.z : ez)
                );

                // applies bounce
                viewer.setSpeed(
                       -viewer.speedX * 0.1f,
                       -viewer.speedY * 0.1f,
                       -viewer.speedZ * 0.1f
                );
                return;
            }
        }
        // sets the player position (no solid block found case)
        viewer.setPosition(ex, ey, ez);
    }
}
