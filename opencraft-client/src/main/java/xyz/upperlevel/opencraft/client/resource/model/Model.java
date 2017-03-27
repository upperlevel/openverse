package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import org.joml.Matrix4f;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Model implements ModelCompiler {

    @Getter
    private Box box = new Box();

    @Getter
    private int vertices = 0;

    @Getter
    private List<ModelPart> parts = new ArrayList<>();

    public Model() {
    }

    public void add(@NonNull ModelPart part) {
        parts.add(part);
        vertices += part.getVertices();
    }

    public void remove(@NonNull ModelPart part) {
        parts.remove(part);
    }

    @Override
    public int compile(Matrix4f trans, ByteBuffer buf) {
        int v = 0;
        for (ModelPart mp : parts)
            v += mp.compile(trans, buf);
        return v;
    }
}
