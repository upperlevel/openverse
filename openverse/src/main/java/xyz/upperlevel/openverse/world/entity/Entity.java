package xyz.upperlevel.openverse.world.entity;

import org.joml.Vector3f;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

public interface Entity {

    EntityType getType();

    World getWorld();

    EntityDriver getDriver();

    void setDriver(EntityDriver driver);

    Location getLocation();

    default void setLocation(Location location) {
        setLocation(location, true);
    }

    void setLocation(Location location, boolean update);

    Vector3f getVelocity();

    void setVelocity(Vector3f velocity);

    default void setRotation(double yaw, double pitch) {
        getLocation().setYaw(yaw);
        getLocation().setPitch(pitch);
    }

    long getId();

    void setId(long id);
}
