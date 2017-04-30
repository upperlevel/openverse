package xyz.upperlevel.openverse.world.entity;

import org.joml.Vector3f;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

public interface Entity {

    EntityType getType();

    World getWorld();

    PlayerDriver getDriver();

    EntityLocation getLocation();

    void setLocation(Location location);

    Vector3f getVelocity();

    void setVelocity(Vector3f velocity);

    default void setRotation(double yaw, double pitch) {
        getLocation().setYaw(yaw);
        getLocation().setPitch(pitch);
    }

    int getId();

    void setId(int id);
}
