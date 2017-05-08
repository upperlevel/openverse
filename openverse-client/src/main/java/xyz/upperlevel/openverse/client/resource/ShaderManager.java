package xyz.upperlevel.openverse.client.resource;

import lombok.NonNull;
import xyz.upperlevel.ulge.opengl.shader.Shader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShaderManager {

    private Map<String, Shader> shaders = new HashMap<>();

    public ShaderManager() {
    }

    public void load(ClientResourceManager resourceManager) {
    }

    public void unload() {
        shaders.clear();
    }

    public ShaderManager register(@NonNull String id, @NonNull Shader shader) {
        shaders.put(id, shader);
        return this;
    }


    public boolean unregister(String id) {
        return shaders.remove(id) != null;
    }

    public Shader getShader(String id) {
        return shaders.get(id);
    }

    public Collection<Shader> getShaders() {
        return shaders.values();
    }
}
