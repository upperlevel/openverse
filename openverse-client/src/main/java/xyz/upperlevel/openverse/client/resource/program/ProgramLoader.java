package xyz.upperlevel.openverse.client.resource.program;

import lombok.Getter;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.Shader;
import xyz.upperlevel.ulge.util.FileUtil;

import java.io.File;

public class ProgramLoader implements ResourceLoader<Program> {
    @Getter
    private final OpenverseClient client;

    public ProgramLoader(OpenverseClient client) {
        this.client = client;
    }

    private Program load(Config config) {
        Program prg = new Program();
        if (config.has("shaders")) {
            for (String shader : config.getStringList("shaders")) {
                Shader shad = client.getResources().shaders().entry(shader);

                if (shad != null) {
                    prg.attach(shad);
                    client.getLogger().info("Attached shader: " + shader);
                }
            }
            prg.link();
            client.getLogger().info("Program linked.");
        }
        return prg;
    }

    @Override
    public Identifier<Program> load(File file) {
        OpenverseClient.get().getLogger().info("Loading program at: " + file.getName());
        return new Identifier<>(FileUtil.stripExtension(file), load(Config.json(file)));
    }
}
