package xyz.upperlevel.openverse.client.render;

import lombok.Getter;
import lombok.experimental.Accessors;
import xyz.upperlevel.openverse.client.render.model.CubeCompiler;
import xyz.upperlevel.openverse.client.render.model.ModelCompilers;
import xyz.upperlevel.openverse.client.render.model.QuadCompiler;
import xyz.upperlevel.openverse.client.render.program.ProgramEnabler;
import xyz.upperlevel.openverse.client.render.texture.TextureContainer;

@Accessors(fluent = true)
public class Rendering {

    private static Rendering get = new Rendering();

    @Getter
    private ProgramEnabler programs = new ProgramEnabler();

    @Getter
    private ModelCompilers models = new ModelCompilers() {
        {
            // registers default compilers
            register(new QuadCompiler());
            register(new CubeCompiler());
        }
    };

    @Getter
    private TextureContainer textures = new TextureContainer(16, 16, 100);

    private Rendering() {
    }

    public static Rendering get() {
        return get;
    }
}