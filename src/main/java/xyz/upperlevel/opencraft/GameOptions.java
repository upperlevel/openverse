package xyz.upperlevel.opencraft;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.ulge.util.math.AngleUtil;

@NoArgsConstructor
public class GameOptions {

    @Getter private float fov = 45f;
    @Getter private int renderDistance = 1;

    public void setFov(float fov) {
        if (fov <= 0)
            throw new IllegalArgumentException("Fov cannot be null/negative");
        this.fov = (float) AngleUtil.normalizeDegAngle(fov);
    }

    public void setRenderDistance(int renderDistance) {
        if (renderDistance <= 0)
            throw new IllegalArgumentException("Render distance cannot be null/negative");
        this.renderDistance = renderDistance;
    }
}
