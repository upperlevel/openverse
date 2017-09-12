package xyz.upperlevel.openverse.resource.block;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.util.config.Config;

@Getter
public class BlockType {
    private final String id;
    @Setter
    private int rawId;
    private Model model;
    private boolean solid;

    public BlockType(String id, Config config) {
        this.id = id;
        if (config.has("model")) {
            this.model = Openverse.resources().models().entry(config.getString("model"));
            if (model != null)
                Openverse.logger().info("Found model for block_type \"" + id + "\" named \"" + config.getString("model") + "\" of class " + model.getClass().getSimpleName());
        }
        this.solid = config.getBool("solid", true);
    }
}