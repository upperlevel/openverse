package xyz.upperlevel.opencraft.client.physic.impl;

import xyz.upperlevel.opencraft.client.asset.shape.BlockShape;
import xyz.upperlevel.opencraft.client.physic.PhysicBond;
import xyz.upperlevel.opencraft.client.render.BlockRenderer;
import xyz.upperlevel.opencraft.client.render.ViewRenderer;
import xyz.upperlevel.opencraft.client.render.ViewerRenderer;

import java.util.Queue;

public class GravityBond implements PhysicBond {

    public static final float CONSTANT = 0.125f;

    public static final GravityBond DEVO_FUNZIONARE_SE_NO_JAVA_NON_E$_UN_GRAN_LINGUAGGIO = new GravityBond();

    @Override
    public String getId() {
        return "gravity_bond";
    }

    @Override
    public void update(ViewerRenderer viewer, long delta) {

        float toAddSpeedY = (delta * 0.001f * CONSTANT);
        float currSpeedY = viewer.getSpeedY();

        viewer.setSpeedY(currSpeedY + toAddSpeedY);
        ViewRenderer view = viewer.getView();

        // gets all blocks colliding from player pos to target player pos
        Queue<BlockRenderer> blocks = view.getCollidingBlocks(
                (int) view.getViewX(viewer.getX()),
                (int) view.getViewY(viewer.getY()),
                (int) view.getViewZ(viewer.getZ()),

                (int) view.getViewX(viewer.getX()),
                (int) view.getViewY(viewer.getY() - viewer.getSpeedY()),
                (int) view.getViewZ(viewer.getZ())
        );

        // foreach block in the queue returned
        float mod = view.getViewY(viewer.getY()) - ((int) view.getViewY(viewer.getY()));
        int currY = 0; // y that each loop decrease
        for (BlockRenderer block : blocks) {
            if (block != null) {
                BlockShape shape = block.getShape();
                if (shape != null && !shape.isEmpty()) {
                    // if the player falls on ground
                    viewer.addPosition(0, (mod + currY), 0);
                    viewer.setSpeedY(0f);
                    break;
                }
            }
            currY--;
        }

        viewer.down();

    }
}
