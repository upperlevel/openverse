package xyz.upperlevel.opencraft.client.physic;

import com.sun.istack.internal.Nullable;
import lombok.NonNull;
import org.joml.Vector3d;
import xyz.upperlevel.opencraft.client.physic.impl.GravityModifier;
import xyz.upperlevel.opencraft.client.physic.util.RaycastBlock;
import xyz.upperlevel.opencraft.client.physic.util.RaycastIterator;
import xyz.upperlevel.opencraft.client.physic.util.RaycastUtil;
import xyz.upperlevel.opencraft.client.render.LocalBlock;
import xyz.upperlevel.opencraft.client.render.LocalWorld;

import java.util.LinkedList;
import java.util.List;

import static xyz.upperlevel.opencraft.client.physic.util.RaycastBlock.Face.*;

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

        RaycastIterator ri = new RaycastIterator(sx, sy, sz, ex, ey, ez);
        while (ri.hasNext()) {
            RaycastBlock rb = ri.next();

            int bx = rb.getX();
            int by = rb.getY();
            int bz = rb.getZ();
            RaycastBlock.Face face = rb.getFace();

            LocalBlock lb = world.getBlock(bx, by, bz);

            if (lb != null && lb.getShape() != null && !lb.getShape().isEmpty()) {
                if (face == null)
                    continue;
                Vector3d it = RaycastUtil.getIntersection(
                        sx, sy, sz,
                        ex, ey, ez,
                        bx, by, bz,
                        face
                );

                viewer.setPosition(
                        (float) ((face == RIGHT && viewer.speedX < 0 || face == LEFT && viewer.speedX >= 0) ? it.x : ex),
                        (float) ((face == UP && viewer.speedY < 0 || face == DOWN && viewer.speedY >= 0) ? it.y : ey ),
                        (float) ((face == FRONT && viewer.speedZ < 0 || face == BACK && viewer.speedZ >= 0) ? it.z : ez)
                );

                viewer.setSpeed(
                        -viewer.speedX * 0.25f,
                        -viewer.speedY * 0.25f,
                        -viewer.speedZ * 0.25f
                );

                return;
            }
        }
        viewer.setPosition(ex, ey, ez);
    }
}
