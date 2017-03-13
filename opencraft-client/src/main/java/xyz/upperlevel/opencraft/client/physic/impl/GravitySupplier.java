package xyz.upperlevel.opencraft.client.physic.impl;

import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.client.physic.PhysicSupplier;
import xyz.upperlevel.opencraft.client.render.BlockRenderer;
import xyz.upperlevel.opencraft.client.render.ViewRenderer;
import xyz.upperlevel.opencraft.client.render.ViewerRenderer;

import java.util.Queue;

public class GravitySupplier implements PhysicSupplier {

    public static final float CONSTANT = 0.125f;

    public static final GravitySupplier DEVO_FUNZIONARE_SE_NO_JAVA_NON_E$_UN_GRAN_LINGUAGGIO = new GravitySupplier();

    @Override
    public String getId() {
        return "gravity_bond";
    }


    @Override
    public void update(ViewerRenderer viewer, long delta) {
        ViewRenderer view = viewer.getView();

        viewer.speedY += CONSTANT * 0.05f;

        float fvx = view.getViewX(viewer.getX());
        float fvy = view.getViewY(viewer.getY());
        float fvz = view.getViewZ(viewer.getZ());

        int vx = (int) fvx;
        int vy = (int) Math.ceil(fvy);
        int vz = (int) fvz;

        // gets all blocks colliding from player pos to target player pos
        Queue<BlockRenderer> blocks = view.getCollidingBlocks(
                vx,
                vy,
                vz,

                vx,
                (int) (vy - viewer.speedY),
                vz
        );

        float mod = vy - fvy;
        int currY = 0; // y that each loop decrease
        for (BlockRenderer block : blocks) {
            if (block != null) {
                BlockShape shape = block.getShape();
                if (shape != null && !shape.isEmpty()) {
                    viewer.addPosition(0f, currY + mod + 1, 0f); // +1 to put the player on surface
                    viewer.speedY = 0;
                    return;
                }
            }
            currY--;
        }

        viewer.down(viewer.speedY);
    }
}
