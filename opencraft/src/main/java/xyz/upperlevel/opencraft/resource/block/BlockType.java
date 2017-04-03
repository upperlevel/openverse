package xyz.upperlevel.opencraft.resource.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.resource.model.Model;

public class BlockType {

    @Getter
    private String id;

    @Getter
    @Setter
    private boolean solid;

    @Getter
    @Setter
    private boolean transparent;

    @Getter
    @Setter
    private Model model;

    public BlockType(@NonNull String id) {
        this.id = id;
    }
}
