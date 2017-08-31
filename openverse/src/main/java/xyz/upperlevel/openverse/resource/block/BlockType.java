package xyz.upperlevel.openverse.resource.block;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.util.config.Config;

@Getter
public class BlockType {
    private final String id;
    private Model model;
    private boolean solid;

    public BlockType(String id, Config config) {
        this.id = id;
        this.model = Openverse.resources().models().entry(config.getString("model"));
        this.solid = config.getBool("solid", true);
    }
}