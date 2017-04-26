package xyz.upperlevel.openverse.client.resource;

import lombok.NonNull;
import xyz.upperlevel.ulge.opengl.shader.Program;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProgramManager {

    private Map<String, Program> programs = new HashMap<>();

    public ProgramManager() {
    }

    public ProgramManager register(@NonNull String id, @NonNull Program program) {
        programs.put(id, program);
        return this;
    }

    public Program get(String id) {
        return programs.get(id);
    }

    public Collection<Program> get() {
        return programs.values();
    }

    public ProgramManager unregister(String id) {
        programs.remove(id);
        return this;
    }

    public void clear() {
        programs.clear();
    }
}