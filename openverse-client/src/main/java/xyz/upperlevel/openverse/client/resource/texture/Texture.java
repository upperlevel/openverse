package xyz.upperlevel.openverse.client.resource.texture;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;

import java.nio.ByteBuffer;

@Getter
@Setter
public class Texture {
    private final ImageContent image;
    private final ByteBuffer data;
    private int layer;

    public Texture(ImageContent image) {
        this.image = image;
        this.data = image.getData();
        this.layer = -1;
    }
}
