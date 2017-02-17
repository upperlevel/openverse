package world.block.defaults;

import org.joml.Vector3f;
import xyz.upperlevel.opencraft.renderer.texture.TextureFragment;
import xyz.upperlevel.opencraft.renderer.texture.Textures;
import world.Zone3f;
import world.BlockComponent;
import world.BlockType;

public class TestBlockType extends BlockType {

    public TestBlockType() {
        super("test_block");
        // creates main block component
        BlockComponent mainBlock = new BlockComponent(new Zone3f(new Vector3f(0), new Vector3f(1f)));
        getShape()
                .addComponent(mainBlock)
                .setBulky(true)
                .setTransparent(false);
        TextureFragment tex = Textures.manager().getFragment(1);
        mainBlock.getFaces().forEach(f -> f.setTexture(tex));
    }
}
