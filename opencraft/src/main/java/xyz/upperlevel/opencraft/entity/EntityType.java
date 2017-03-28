package xyz.upperlevel.opencraft.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.shape.Model;

public class EntityType {

    @Getter
    private String id;

    @Getter
    @Setter
    private Model model;

    public EntityType(@NonNull String id) {
        this.id = id;
    }
}