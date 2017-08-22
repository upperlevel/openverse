package xyz.upperlevel.openverse.client.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.resource.EntityType;

@Getter
@RequiredArgsConstructor
public class EntityRenderer {
    private final int id;
    private final EntityType type;

    public void render() {
        // todo
    }
}
