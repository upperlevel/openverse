package xyz.upperlevel.opencraft.client.asset.texture;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TextureRegistry {

    private Map<String, Texture> textures = new HashMap<>();

    public TextureRegistry() {
    }

    public void register(@NonNull Texture texture) {
        textures.put(texture.getId(), texture);
    }

    public Texture getTexture(String id) {
        return textures.get(id);
    }

    public void unregister(String id) {
        textures.remove(id);
    }

    public void unregister(@NonNull Texture texture) {
        unregister(texture.getId());
    }

    public Collection<Texture> getTextures() {
        return textures.values();
    }
}
