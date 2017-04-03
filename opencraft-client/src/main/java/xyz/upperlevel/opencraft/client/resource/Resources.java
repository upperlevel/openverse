package xyz.upperlevel.opencraft.client.resource;

import lombok.Getter;
import lombok.experimental.Accessors;
import xyz.upperlevel.opencraft.resource.model.Models;
import xyz.upperlevel.opencraft.client.resource.program.Programs;
import xyz.upperlevel.opencraft.client.resource.texture.Textures;
import xyz.upperlevel.opencraft.resource.block.BlockTypes;
import xyz.upperlevel.opencraft.resource.entity.EntityTypes;

@Accessors(fluent = true)
public final class Resources {

    private static Resources get = new Resources();

    @Getter
    private Textures textures = new Textures();

    @Getter
    private Models models = new Models();

    @Getter
    private Programs programs = new Programs();

    @Getter
    private BlockTypes blocks = new BlockTypes();

    @Getter
    private EntityTypes entities = new EntityTypes();

    public Resources() {
    }

    public void load() {
        textures.load(this);
        programs.load(this);
    }

    public void unload() {
        textures.unload();
        programs.unload();
    }

    public static Resources get() {
        return get;
    }
}
