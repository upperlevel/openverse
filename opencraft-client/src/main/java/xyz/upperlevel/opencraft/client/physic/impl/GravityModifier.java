package xyz.upperlevel.opencraft.client.physic.impl;

import xyz.upperlevel.opencraft.client.physic.SpeedModifier;
import xyz.upperlevel.opencraft.client.physic.PhysicalViewer;

public class GravityModifier implements SpeedModifier {

    public static final float CONSTANT = 0.125f;

    public GravityModifier() {
    }

    @Override
    public void update(PhysicalViewer viewer) {
        viewer.addSpeedY(-CONSTANT);
    }
}