package xyz.upperlevel.openverse.client.resource.model;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.client.resource.TextureBakery;
import xyz.upperlevel.openverse.resource.model.Model;

import java.nio.ByteBuffer;

/**
 * This class handles vertices and other render stuff
 * related to the model.
 */
public class ClientModel extends Model<ClientModelPart> implements ModelCompiler {

    public ClientModel(String id) {
        super(id);
    }

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
    public int compile(TextureBakery bakery, Matrix4f in, ByteBuffer out) {
        // doesn't do anything with the input matrix
        int vrt = 0;
        for (ClientModelPart part : getParts())
            vrt += part.compile(bakery, in, out);
        return vrt;
    }
}
