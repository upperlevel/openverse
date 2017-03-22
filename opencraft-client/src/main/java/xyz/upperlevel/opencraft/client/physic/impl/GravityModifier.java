package xyz.upperlevel.opencraft.client.physic.impl;

import xyz.upperlevel.opencraft.client.physic.PhysicModifier;
import xyz.upperlevel.opencraft.client.physic.PhysicalViewer;

public class GravityModifier implements PhysicModifier {

    public static final float CONSTANT = 0.125f;

    public GravityModifier() {
    }

    @Override
    public void update(PhysicalViewer viewer) {
        viewer.speedY -= CONSTANT * 0.5f;
    }
}