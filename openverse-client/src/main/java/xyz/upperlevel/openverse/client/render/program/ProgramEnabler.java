package xyz.upperlevel.openverse.client.render.program;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.openverse.client.resource.program.Programs;
import xyz.upperlevel.ulge.opengl.shader.Program;

public class ProgramEnabler {

    @Getter
    private Program enabled = null;

    public ProgramEnabler() {
    }

    private Program get(@NonNull Programs programs, String id) {
        return programs.get(id);
    }

    public void enable(@NonNull Programs programs, String id) {
        enable(get(programs, id));
    }

    public void enable(Program program) {
        program.bind();
        enabled = program;
    }

    public void disable(@NonNull Programs programs, String id) {
        disable(get(programs, id));
    }

    public void disable(Program program) {
        program.unbind();
        enabled = null;
    }
}
