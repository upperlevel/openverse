package xyz.upperlevel.openverse.client.render;

import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.resource.Texture;
import xyz.upperlevel.openverse.client.resource.event.TextureAddEvent;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_RGBA8;

public class TextureBakery implements Listener {

    private final List<Texture> layers = new ArrayList<>(100);
    private final Texture2dArray array;

    public TextureBakery() {
        array = new Texture2dArray();
        array.allocate(4, GL_RGBA8, 16, 16, 100);

        OpenverseClient.get().getEventManager().register(this);
    }

    public int getLayer(@NonNull Texture texture) {
        return layers.indexOf(texture);
    }

    public void bake(@NonNull Texture texture) {
        array.load(layers.size(), texture.getImage());
        layers.add(texture);
    }

    @EventHandler
    public void onTextureAdd(TextureAddEvent event) {
        bake(event.getTexture());
    }

    // textures cannot be removed
}
