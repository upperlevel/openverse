package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import xyz.upperlevel.openverse.resource.model.Model;

@RequiredArgsConstructor
public class BlockType<M extends Model> {

    @Getter
    @NonNull
    private final String id;

    @Getter
    @Setter
    private M model;
}
