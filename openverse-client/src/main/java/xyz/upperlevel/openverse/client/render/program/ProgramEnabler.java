package xyz.upperlevel.openverse.client.render.program;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.client.resource.ProgramManager;
import xyz.upperlevel.ulge.opengl.shader.Program;

public class ProgramEnabler {

    @Getter
    private Program enabled = null;

    public ProgramEnabler() {
    }

    private Program get(@NonNull ProgramManager programManager, String id) {
        return programManager.get(id);
    }

    public void enable(@NonNull ProgramManager programManager, String id) {
        enable(get(programManager, id));
    }

    public void enable(Program program) {
        program.bind();
        enabled = program;
    }

    public void disable(@NonNull ProgramManager programManager, String id) {
        disable(get(programManager, id));
    }

    public void disable(Program program) {
        program.unbind();
        enabled = null;
    }
}
