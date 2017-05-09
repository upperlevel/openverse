package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.event.EventHandler;
import xyz.upperlevel.event.Listener;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.client.resource.Texture;
import xyz.upperlevel.openverse.client.resource.event.TextureAddEvent;
import xyz.upperlevel.ulge.opengl.texture.Texture2dArray;

import java.util.ArrayList;
import java.util.List;

public class TextureBakery implements Listener {

    @Getter
    private final OpenverseClient client;

    private final List<Texture> layers = new ArrayList<>();
    private final Texture2dArray array = new Texture2dArray();

    public TextureBakery(OpenverseClient client) {
        this.client = client;
        client.getEventManager().register(this);
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
}
