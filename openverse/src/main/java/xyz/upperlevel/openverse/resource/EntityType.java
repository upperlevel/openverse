package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.openverse.resource.model.impl.NodeModel;

public class EntityType {

    @Getter
    private String id;

    @Getter
    @Setter
    private NodeModel model;

    public EntityType(@NonNull String id) {
        this.id = id;
    }
}
