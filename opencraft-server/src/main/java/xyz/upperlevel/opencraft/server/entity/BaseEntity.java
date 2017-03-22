package xyz.upperlevel.opencraft.server.entity;

public class BaseEntity extends Entity {


    @Override
    public void tick() {

    }

    public void tickPhysic() {
        getLoc().add(getVel());
    }
}
