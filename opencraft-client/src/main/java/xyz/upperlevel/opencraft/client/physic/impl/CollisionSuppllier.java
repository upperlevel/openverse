package xyz.upperlevel.opencraft.client.physic.impl;

import xyz.upperlevel.opencraft.client.physic.PhysicSupplier;
import xyz.upperlevel.opencraft.client.render.BlockRenderer;
import xyz.upperlevel.opencraft.client.render.ViewerRenderer;

public class CollisionSuppllier implements PhysicSupplier {

    @Override
    public String getId() {
        return "physic_supplier";
    }

    @Override
    public void update(ViewerRenderer viewer, long delta) {
        BlockRenderer br = viewer.getBlock();
        if (!br.isEmpty()) {

        }
    }
}