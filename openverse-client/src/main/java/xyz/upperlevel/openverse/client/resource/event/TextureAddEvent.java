package xyz.upperlevel.openverse.client.resource.event;

import lombok.Data;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.client.resource.Texture;

@Data
public class TextureAddEvent implements Event {

    private final Texture texture;
}
