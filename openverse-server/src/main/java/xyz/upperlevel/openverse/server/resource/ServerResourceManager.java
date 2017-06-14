package xyz.upperlevel.openverse.server.resource;

import xyz.upperlevel.openverse.resource.*;
import xyz.upperlevel.openverse.resource.model.Cube;
import xyz.upperlevel.openverse.resource.model.Model;

public class ServerResourceManager extends ResourceManager {

    @Override
    public void onLoad() {
        // here we load our server resource (not from file at the moment)
        // todo from file

        // REGISTERS TEST ENTITY MODEL
        Model entityModel = new Model("player")
                .add(new Cube());
        getModelManager()
                .register(entityModel);

        // REGISTERS TEST ENTITY
        getEntityTypeManager()
                .register(new EntityType("player")
                        .setModel(entityModel));

        // REGISTERS TEST BLOCK MODEL
        Model blockModel = new Model("hello_world")
                .add(new Cube());
        getModelManager()
                .register(blockModel);

        // REGISTERS TEST BLOCK
        getBlockTypeManager()
                .register(new BlockType("hello_world", true)
                        .setModel(blockModel));
    }
}
