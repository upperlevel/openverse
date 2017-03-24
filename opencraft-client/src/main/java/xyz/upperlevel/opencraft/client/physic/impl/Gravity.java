package xyz.upperlevel.opencraft.client.physic.impl;

import xyz.upperlevel.opencraft.server.entity.Entity;
import xyz.upperlevel.opencraft.client.physic.Force;

public class Gravity implements Force {

    public Gravity() {
    }

    @Override
    public void update(Entity e, long delta) {
        e.getVel().y = -.1f;
    }
}