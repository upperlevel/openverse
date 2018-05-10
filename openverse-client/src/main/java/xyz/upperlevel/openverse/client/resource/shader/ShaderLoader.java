package xyz.upperlevel.openverse.client.resource.shader;

import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.ulge.opengl.shader.Shader;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class ShaderLoader implements ResourceLoader<Shader> {
    @Override
    public Identifier<Shader> load(File file) {
        Shader shader = new Shader(ShaderType.getFromExtension(FileUtil.getExtension(file)));
        Openverse.getLogger().info("Loading shader at: " + file.getName());
        try {
            shader.linkSource(file);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot link source on file \"" + file + "\": " + e);
        }
        shader.compileSource();
        if (shader.getCompileStatus().isOk())
            Openverse.getLogger().info("Shader status: " + shader.getCompileStatus().getLog());
        else
            throw new IllegalArgumentException("Error compiling shader \"" + file + "\": " + shader.getCompileStatus().getLog());
        return new Identifier<>(file.getName(), shader);
    }
}
