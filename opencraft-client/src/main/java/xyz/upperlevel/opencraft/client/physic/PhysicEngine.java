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

        float fx = viewer.x;
        float fy = viewer.y;
        float fz = viewer.z;

        float tx = viewer.x + viewer.speedX;
        float ty = viewer.y + viewer.speedY;
        float tz = viewer.z + viewer.speedZ;

        System.out.println("fx: " + fx + " fy: " + fy + " fz: " + fz);
        System.out.println("tx: " + tx + " ty: " + ty + " tz: " + tz);
        RaycastIterator ri = new RaycastIterator(fx, fy, fz, tx, ty, tz);
        while (ri.hasNext()) {
            RaycastBlock rb = ri.next();

            int bx = rb.getX();
            int by = rb.getY();
            int bz = rb.getZ();
            RaycastBlock.Face face = rb.getFace();

            LocalBlock lb = world.getBlock(bx, by, bz);

            if (lb != null && lb.getShape() != null && !lb.getShape().isEmpty()) {
                if (face == null)
                    return;
                Vector3d it = RaycastUtil.getIntersection(
                        fx, fy, fz,
                        tx, ty, tz,
                        bx, by, bz,
                        face
                );
                viewer.setPosition((float) it.x, (float) it.y, (float) it.z);
                viewer.setSpeed(
                        -viewer.speedX * 0.9f,
                        -viewer.speedY * 0.9f,
                        -viewer.speedZ * 0.9f
                );
                return;
            }
        }
        viewer.setPosition(tx, ty, tz);
    }
}
