package xyz.upperlevel.opencraft.common.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.common.shape.Model;

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