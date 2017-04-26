package xyz.upperlevel.openverse.world.entity;

import org.joml.Vector3f;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

public interface Entity {

    EntityType getType();

    Location getLocation();

    void setLocation(Location location);

    Vector3f getVelocity();

    void setVelocity(Vector3f velocity);


    double getYaw();

    void setYaw(double yaw);

    double getPitch();

    void setPitch(double pitch);

    void setRotation(double yaw, double pitch);

    World getWorld();


    int getId();

    void setId(int id);
}
