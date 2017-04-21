package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.openverse.resource.model.Model;

public class BlockType {

    @Getter
    private String id;

    @Getter
    @Setter
    private boolean solid = true;

    @Getter
    @Setter
    private boolean transparent = false;

    @Getter
    @Setter
    private Model model;

    public BlockType(@NonNull String id) {
        this.id = id;
    }
}
