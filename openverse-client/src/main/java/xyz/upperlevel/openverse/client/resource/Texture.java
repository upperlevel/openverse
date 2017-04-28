package xyz.upperlevel.openverse.client.resource;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

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

    public ByteBuffer getImageData() {
        return image.getData();
    }

    // dimensions in pixels

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}