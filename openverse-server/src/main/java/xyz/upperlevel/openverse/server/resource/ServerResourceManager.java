package xyz.upperlevel.openverse.server.resource;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.*;
import xyz.upperlevel.openverse.resource.model.Cube;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.resource.model.ModelManager;
import xyz.upperlevel.openverse.resource.model.Shape;

public class ServerResourceManager extends ResourceManager {

    @Getter
    private final ServerModelManager modelManager = new ServerModelManager();

    @Override
    public void onLoad() {
        // REGISTERS TEST ENTITY MODEL
        Model entityModel = new Model<>("player").add(new Cube());
        modelManager.register(entityModel);

        // REGISTERS TEST ENTITY
        getEntityTypeManager()
                .register(new EntityType("player")
                        .setModel(entityModel));

        // REGISTERS TEST BLOCK MODEL
        Model blockModel = new Model<>("hello_world")
                .add(new Cube());
        modelManager.register(blockModel);

        // REGISTERS TEST BLOCK
        getBlockTypeManager()
                .register(new BlockType("hello_world", true)
                        .setModel(blockModel));
    }
}
