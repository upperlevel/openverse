package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelNode implements Model {

    @Getter
    private String id;

    @Getter
    private Box box = new Box();

    @Getter
    private List<Model> models = new ArrayList<>();

    public ModelNode(@NonNull String id) {
        this.id = id;
    }

    public void add(@NonNull Model m) {
        models.add(m);
    }

    public void remove(@NonNull Model m) {
        models.remove(m);
    }
}
