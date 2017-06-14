package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.openverse.resource.model.Model;

@Accessors(chain = true)
public class BlockType {
    public static final int MAX_ID_LENGTH = 32;

    @Getter
    @NonNull
    private final String id;

    @Getter
    private final boolean solid;

    @Getter
    @Setter
    private Model model;

    public BlockType(String id, boolean solid) {
        if(id == null || id.isEmpty())
            throw new IllegalArgumentException("id is empty!");
        if(id.length() > MAX_ID_LENGTH)
            throw new IllegalArgumentException("id length better than " + MAX_ID_LENGTH);
        this.id = id;
        this.solid = solid;
    }
}
