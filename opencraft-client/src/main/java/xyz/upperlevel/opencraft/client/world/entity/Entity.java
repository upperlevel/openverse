package xyz.upperlevel.opencraft.client.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Entity<T extends EntityType> {

    private static long currentId = 0;

    @Getter
    private T type;

    @Getter
    private long id;

    @Getter
    @Setter
    private float x, y, z;

    @Getter
    @Setter
    private float yaw, pitch;

    public Entity(@NonNull T type) {
        this.type = type;
        this.id = currentId++;
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setPosition(float x, float y, float z, float yaw, float pitch) {
        setPosition(x, y, z);
        setRotation(yaw, pitch);
    }
}