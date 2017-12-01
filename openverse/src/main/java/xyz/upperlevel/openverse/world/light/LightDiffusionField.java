package xyz.upperlevel.openverse.world.light;

import xyz.upperlevel.openverse.world.LightType;

public interface LightDiffusionField {
    int getLightAt(int x, int y, int z, LightType lightType);

    void setLightAt(int x, int y, int z, int lightLevel, LightType lightType);
}
