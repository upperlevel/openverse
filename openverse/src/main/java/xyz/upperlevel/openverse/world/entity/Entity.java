package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.resource.EntityType;
import xyz.upperlevel.openverse.world.Location;

public class Entity {

    @Getter
    private EntityType type;

    @Getter
    private Location location = new Location();

    @Getter
    private Vector3f velocity = new Vector3f();

    @Getter
    @Setter
    private int id = -1;

    public Entity(@NonNull EntityType type) {
        this.type = type;
    }

    public void update() {
    }
}