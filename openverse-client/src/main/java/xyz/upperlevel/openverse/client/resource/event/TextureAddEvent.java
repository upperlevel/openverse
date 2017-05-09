package xyz.upperlevel.openverse.client.resource.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.client.resource.Texture;

@RequiredArgsConstructor
public class TextureAddEvent {

    @Getter
    private final Texture texture;
}
