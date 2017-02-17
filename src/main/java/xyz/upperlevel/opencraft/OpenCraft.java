package xyz.upperlevel.opencraft;

import lombok.Getter;
import xyz.upperlevel.opencraft.renderer.texture.TextureManager;
import xyz.upperlevel.opencraft.renderer.texture.Textures;
import xyz.upperlevel.opencraft.util.event.ListenerManager;

public class OpenCraft {

    public static final OpenCraft $ = new OpenCraft(); // todo tmp

    @Getter
    public final TextureManager textureManager = Textures.manager();

    public static OpenCraft $() {
        return $;
    }
}
