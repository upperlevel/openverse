package xyz.upperlevel.opencraft.client.physic;

import xyz.upperlevel.opencraft.client.render.ViewerRenderer;

public interface PhysicBond {

    String getId();

    void update(ViewerRenderer viewer, long delta);
}