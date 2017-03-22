package xyz.upperlevel.opencraft.server.physic.impl;

import xyz.upperlevel.opencraft.server.physic.SpeedModifier;
import xyz.upperlevel.opencraft.server.physic.PhysicalViewer;

public class GravityModifier implements SpeedModifier {

    public static final float CONSTANT = 0.125f;

    public GravityModifier() {
    }

    @Override
    public void update(PhysicalViewer viewer) {
        viewer.addSpeedY(-CONSTANT);
    }
}