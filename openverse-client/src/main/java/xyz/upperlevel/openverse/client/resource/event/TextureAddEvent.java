package xyz.upperlevel.openverse.client.resource.event;

import lombok.Data;
import xyz.upperlevel.openverse.client.resource.Texture;

@Data
public class TextureAddEvent {

    private final Texture texture;
}
