package xyz.upperlevel.opencraft.client.asset.texture;

import lombok.Getter;
import lombok.Setter;

public class BakeTexture {

    @Getter
    private int pixelWidth, pixelHeight;

    @Getter
    @Setter
    private float width, height;

    @Getter
    @Setter
    private float minU, minV, maxU, maxV;

    public BakeTexture(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }
}