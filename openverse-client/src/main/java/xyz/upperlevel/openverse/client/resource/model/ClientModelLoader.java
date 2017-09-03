package xyz.upperlevel.openverse.client.resource.model;

import xyz.upperlevel.openverse.resource.Identifier;
import xyz.upperlevel.openverse.resource.ResourceLoader;
import xyz.upperlevel.openverse.resource.model.ModelLoader;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.openverse.util.file.FileUtils;

import java.io.File;

public class ClientModelLoader extends ModelLoader<ClientModel> {
    @Override
    public Identifier<ClientModel> load(File file) {
        return new Identifier<>(
                FileUtils.stripExtension(file),
                ClientModel.clientDeserialize(Config.json(file))
        );
    }
}
