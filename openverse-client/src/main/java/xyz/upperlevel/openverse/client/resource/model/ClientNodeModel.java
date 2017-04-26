package xyz.upperlevel.openverse.client.resource.model;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.resource.model.impl.NodeModel;

import java.nio.ByteBuffer;

/**
 * This class handles vertices and other render stuff
 * related to the model.
 */
public class ClientNodeModel extends NodeModel<ClientModelPart> implements ModelCompiler {

    public int getVerticesCount() {
        int vrt = 0;
        for (ClientModelPart part : getParts())
            vrt += part.getVerticesCount();
        return vrt;
    }

    public int getDataCount() {
        int dt = 0;
        for (ClientModelPart part : getParts())
            dt += part.getDataCount();
        return dt;
    }

    /**
     * Loads vertices of the current model in the ByteBuffer passed and
     * returns the number of vertices added.
     */
    @Override
    public int compile(Matrix4f in, ByteBuffer out) {
        int vrt = 0;
        for (ClientModelPart part : getParts())
            vrt += part.compile(in, out);
        return vrt;
    }
}
