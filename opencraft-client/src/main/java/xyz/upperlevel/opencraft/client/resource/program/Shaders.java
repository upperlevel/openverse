package xyz.upperlevel.opencraft.client.resource.program;

import lombok.NonNull;
import xyz.upperlevel.opencraft.client.resource.Resources;
import xyz.upperlevel.ulge.opengl.shader.Shader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Shaders {

    private Map<String, Shader> shaders = new HashMap<>();

    public Shaders() {
    }

    public void load(Resources resources) {
    }

    public void unload() {
        shaders.clear();
    }

    public Shaders register(@NonNull String id, @NonNull Shader shader) {
        shaders.put(id, shader);
        return this;
    }

    public Shader get(String id) {
        return shaders.get(id);
    }

    public Collection<Shader> get() {
        return shaders.values();
    }

    public void unregister(String id) {
        shaders.remove(id);
    }
}
