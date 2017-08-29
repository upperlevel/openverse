package xyz.upperlevel.openverse.client.resource.shader;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.Resources;
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
        try {
            shader.linkSource(file);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot link source on file \"" + file + "\": " + e);
        }
        shader.compileSource();
        return new Identifier<>(FileUtil.stripExtension(file), shader);
    }
}
