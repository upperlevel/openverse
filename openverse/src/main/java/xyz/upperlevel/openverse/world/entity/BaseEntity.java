package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;

public class BaseEntity implements Entity {

    @Getter
    private final EntityType type;

    @Setter
    private Location location = new Location();

    @Setter
    private Vector3f velocity = new Vector3f();

    @Getter
    @Setter
    private int id = -1;

    public BaseEntity(@NonNull EntityType type) {
        this.type = type;
    }

    public void update() {
    }

    @Override
    public Location getLocation() {
        return new Location(location);
    }

    @Override
    public Vector3f getVelocity() {
        return new Vector3f(velocity);
    }

    @Override
    public double getYaw() {
        return location.yaw;
    }

    @Override
    public void setYaw(double yaw) {
        location.yaw = yaw;
    }

    @Override
    public double getPitch() {
        return location.pitch;
    }

    @Override
    public void setPitch(double pitch) {
        location.pitch = pitch;
    }

    @Override
    public void setRotation(double yaw, double pitch) {
        location.yaw = yaw;
        location.pitch = pitch;
    }

    @Override
    public World getWorld() {
        return location.world();
    }
}