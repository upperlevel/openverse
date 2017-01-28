package xyz.upperlevel.opencraft.render.block;

import xyz.upperlevel.opencraft.world.Block;

public class SimpleBlockRenderer implements BlockRenderer {

    public static final BlockRenderer $ = new SimpleBlockRenderer();

    @Override
    public void render(Block block) {
        // todo
    }

    public static BlockRenderer $() {
        return $;
    }
}