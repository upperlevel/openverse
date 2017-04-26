package xyz.upperlevel.openverse.client.resource.model;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.openverse.resource.model.impl.CubeFacePosition;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class ClientCubeModel implements ClientModelPart {

    private Map<CubeFacePosition, > faces = new HashMap<>() {

    }

    @Getter
    @Setter
    private Box box;

    public ClientCubeModel() {
    }

    @Override
    public int getVerticesCount() {
        return 8;
    }

    @Override
    public int getDataCount() {
        return -1; // todo 8 * 4;
    }

    @Override
    public int compile(Matrix4f in, ByteBuffer out) {


        return 0;
    }
}
