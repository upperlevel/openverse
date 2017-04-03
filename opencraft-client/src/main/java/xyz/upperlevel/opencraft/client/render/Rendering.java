package xyz.upperlevel.opencraft.client.render;

import lombok.Getter;
import lombok.experimental.Accessors;
import xyz.upperlevel.opencraft.client.render.model.CubeCompiler;
import xyz.upperlevel.opencraft.client.render.model.ModelCompilers;
import xyz.upperlevel.opencraft.client.render.model.QuadCompiler;
import xyz.upperlevel.opencraft.client.render.program.ProgramEnabler;
import xyz.upperlevel.opencraft.client.render.texture.TextureContainer;

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