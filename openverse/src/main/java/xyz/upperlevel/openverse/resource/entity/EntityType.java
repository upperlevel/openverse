package xyz.upperlevel.openverse.resource.entity;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.util.config.Config;

@Getter
public class EntityType {
    private Model model;

    public EntityType(Config config) {
        this.model = Openverse.resources().models().entry(config.getString("model"));
    }
}
