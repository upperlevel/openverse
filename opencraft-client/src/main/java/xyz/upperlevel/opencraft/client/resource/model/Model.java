package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

import java.util.ArrayList;
import java.util.List;

public class Model implements ModelPart {

    @Getter
    private String id;

    @Getter
    private Box box = new Box();

    @Getter
    private List<ModelPart> models = new ArrayList<>();

    public Model(@NonNull String id) {
        this.id = id;
    }

    public void add(@NonNull ModelPart m) {
        models.add(m);
    }

    public void remove(@NonNull ModelPart m) {
        models.remove(m);
    }
}
