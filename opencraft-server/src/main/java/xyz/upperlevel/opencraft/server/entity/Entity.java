package xyz.upperlevel.opencraft.server.entity;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.server.world.Location;

public class Entity {

    @Getter
    private EntityType type;

    @Getter
    private Location loc = new Location();

    @Getter
    private Vector3f vel = new Vector3f();

    public Entity(@NonNull EntityType type) {
        this.type = type;
    }

    public void update() {
    }
}