package xyz.upperlevel.openverse.client.resource.texture;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;

public class Texture {

    @Getter
    private String id;

    @Getter
    @Setter
    private ImageContent image;

    public Texture(@NonNull String id, @NonNull ImageContent image) {
        this.id = id;
        this.image = image;
    }
}