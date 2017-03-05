package xyz.upperlevel.opencraft.client.asset.texture;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.image.BufferedImage;

public class Texture {

    @Getter
    private String id;

    @Getter
    private BufferedImage image;

    @Getter
    @Setter
    private int
            width,
            height;

    @Getter
    @Setter
    private float
            screenWidth  = 1f,
            screenHeight = 1f;

    @Getter
    @Setter
    private float
            minU = 0f,
            minV = 0f,
            maxU = 1f,
            maxV = 1f;

    public Texture(@NonNull String id, @NonNull BufferedImage image) {
        this.id    = id;
        this.image = image;
        width      = image.getWidth();
        height     = image.getHeight();
    }
}