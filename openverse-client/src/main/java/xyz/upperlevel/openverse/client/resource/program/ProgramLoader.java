package xyz.upperlevel.openverse.client.resource.program;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Shader;
import xyz.upperlevel.ulge.util.FileUtil;

import java.io.File;

public class ProgramLoader implements ResourceLoader<Program> {
    private static Program load(Config config) {
        Program prg = new Program();
        if (config.has("shaders")) {
            for (String shader : config.getStringList("shaders")) {
                Shader shad = ((ClientResources) Openverse.resources()).shaders().entry(shader);

                if (shad != null) {
                    prg.attach(shad);
                    Openverse.getLogger().info("Attached shader: " + shader);
                }
            }
            prg.link();
            Openverse.getLogger().info("Program linked.");
        }
        return prg;
    }

    @Override
    public Identifier<Program> load(File file) {
        Openverse.getLogger().info("Loading program at: " + file.getName());
        return new Identifier<>(
                FileUtil.stripExtension(file),
                load(Config.json(file))
        );
    }
}
