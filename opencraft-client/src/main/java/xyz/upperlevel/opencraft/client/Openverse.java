package xyz.upperlevel.opencraft.client;

import lombok.Getter;
import xyz.upperlevel.opencraft.client.resource.ResourceManager;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;

public class Openverse {

    public static final Openverse instance = new Openverse();

    @Getter
    private ResourceManager resources = new ResourceManager();

    private Openverse() {
        Texture t = Openverse.g()
                .getResources()
                .getTextures()
                .get("id");
    }

    public static Openverse g() {
        return instance;
    }
}
