package xyz.upperlevel.openverse.world.entity;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.world.Location;

public abstract class EntityType {
    @Getter
    private final String id;
    @Getter
    @Setter
    private int rawId = -1;

    public EntityType(String id) {
        this.id = id;
    }

    public abstract Entity spawn(Location loc);

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
