package xyz.upperlevel.opencraft.server.entity;

import xyz.upperlevel.opencraft.server.physic.util.PhysicalFace;

public class RigidEntity extends Entity {

    private boolean[] collides = new boolean[PhysicalFace.values().length];

    public RigidEntity() {
    }

    public boolean isColliding(PhysicalFace face) {
        return collides[face.ordinal()];
    }

    public void setColliding(PhysicalFace face, boolean colliding) {
        collides[face.ordinal()] = colliding;
    }

    @Override
    public void tick() {

    }
}
