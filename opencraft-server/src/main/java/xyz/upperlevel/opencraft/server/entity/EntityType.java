package xyz.upperlevel.opencraft.server.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.server.shape.Shape;

public class EntityType {

    @Getter
    private String id;

    @Getter
    @Setter
    private Shape shape;

    public EntityType(@NonNull String id) {
        this.id = id;
    }
}