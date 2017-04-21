package xyz.upperlevel.openverse.client.resource.program;

import lombok.NonNull;
import xyz.upperlevel.openverse.client.resource.ResourceManager;
import xyz.upperlevel.ulge.opengl.shader.Program;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Programs {

    private Map<String, Program> programs = new HashMap<>();

    public Programs() {
    }

    public void load(ResourceManager resourceManager) {
    }

    public void unload() {
        programs.clear();
    }

    public Programs register(@NonNull String id, @NonNull Program program) {
        programs.put(id, program);
        return this;
    }

    public Program get(String id) {
        return programs.get(id);
    }

    public Collection<Program> get() {
        return programs.values();
    }

    public Programs unregister(String id) {
        programs.remove(id);
        return this;
    }
}