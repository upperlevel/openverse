package xyz.upperlevel.openverse.world.light;

import xyz.upperlevel.openverse.world.LightType;
import xyz.upperlevel.openverse.world.World;

public class WorldDiffusionField implements LightDiffusionField {
    private final World world;

    public WorldDiffusionField(World world) {
        this.world = world;
    }

    @Override
    public int getLightAt(int x, int y, int z, LightType lightType) {
        return lightType.getLightAt(world, x, y, z);
    }

    @Override
    public void setLightAt(int x, int y, int z, int lightLevel, LightType lightType) {
        lightType.setLightAt(world, x, y, z, lightLevel, false);
    }
}
