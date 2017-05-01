package xyz.upperlevel.openverse.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.openverse.resource.model.Model;

@Accessors(chain = true)
@RequiredArgsConstructor
public class EntityType {

    @Getter
    private final String id;

    @Getter
    @Setter
    private Model model;
}
