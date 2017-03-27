package xyz.upperlevel.opencraft.common.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.common.world.Location;

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