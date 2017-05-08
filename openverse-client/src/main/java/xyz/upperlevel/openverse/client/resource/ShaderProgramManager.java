package xyz.upperlevel.openverse.client.resource;

import lombok.NonNull;
import xyz.upperlevel.ulge.opengl.shader.Program;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShaderProgramManager {

    private Map<String, Program> programs = new HashMap<>();

    public ShaderProgramManager() {
    }

    public ShaderProgramManager register(@NonNull String id, @NonNull Program program) {
        programs.put(id, program);
        return this;
    }

    public boolean unregister(String id) {
        return programs.remove(id) != null;
    }

    public Program getProgram(String id) {
        return programs.get(id);
    }

    public Collection<Program> getPrograms() {
        return programs.values();
    }

    public void clear() {
        programs.clear();
    }
}