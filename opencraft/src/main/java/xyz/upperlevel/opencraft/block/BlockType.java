package xyz.upperlevel.opencraft.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.shape.Model;

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