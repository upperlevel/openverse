package xyz.upperlevel.openverse.client.resource.model;

import lombok.Getter;
import xyz.upperlevel.openverse.resource.model.ModelRegistry;

import java.util.logging.Logger;

@Getter
public class ClientModelRegistry extends ModelRegistry<ClientModel> {
    public static final ClientModelLoader LOADER = new ClientModelLoader();

    public ClientModelRegistry(Logger logger) {
        super(logger);
    }
}
