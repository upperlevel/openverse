package xyz.upperlevel.openverse.world;

public enum LightType {
    BLOCK_LIGHT {
        @Override
        public int getLightAt(World world, int x, int y, int z) {
            return world.getBlockLight(x, y, z);
        }

        @Override
        public void setLightAt(World world, int x, int y, int z, int lightLevel, boolean diffuse) {
            world.setBlockLight(x, y, z, lightLevel, diffuse);
        }
    },
    BLOCK_SKYLIGHT {
        @Override
        public int getLightAt(World world, int x, int y, int z) {
            return world.getBlockSkylight(x, y, z);
        }

        @Override
        public void setLightAt(World world, int x, int y, int z, int lightLevel, boolean diffuse) {
            world.setBlockSkylight(x, y, z, lightLevel, diffuse);
        }
    };

    public abstract int getLightAt(World world, int x, int y, int z);

    public abstract void setLightAt(World world, int x, int y, int z, int lightLevel, boolean diffuse);
}
