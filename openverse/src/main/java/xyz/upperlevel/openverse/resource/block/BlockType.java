package xyz.upperlevel.openverse.resource.block;

import lombok.Getter;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.util.config.Config;

@Getter
public class BlockType {
    private Model model;
    private boolean solid;

    public BlockType(Config config) {
        this.model = Openverse.resources().models().entry(config.getString("model"));
        this.solid = config.getBool("solid", true);
    }
}