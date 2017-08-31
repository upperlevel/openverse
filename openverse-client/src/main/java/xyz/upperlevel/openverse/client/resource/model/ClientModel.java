package xyz.upperlevel.openverse.client.resource.model;

import org.joml.Matrix4f;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.resource.ClientResources;
import xyz.upperlevel.openverse.client.resource.model.shape.ClientShape;
import xyz.upperlevel.openverse.client.resource.model.shape.ClientShapeType;
import xyz.upperlevel.openverse.client.resource.texture.Texture;
import xyz.upperlevel.openverse.resource.model.Model;
import xyz.upperlevel.openverse.util.config.Config;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

/**
 * This class has a model only for client-side.
 * It's a {@link ClientShape} and can be added to another {@link Model}.
 * It's deserialized differently from the common {@link Model} (the same of the server side), because it
 * has a color and a texture.
 */
public class ClientModel extends Model<ClientShape> implements ClientShape {
    @Override
    public int getVerticesCount() {
        int cnt = 0;
        for (ClientShape shape : getShapes())
            cnt += shape.getVerticesCount();
        Openverse.logger().info("Model vertices!!! " + cnt);
        return cnt;
    }

    @Override
    public void setColor(Color color) {
        getShapes().forEach(shape -> shape.setColor(color));
    }

    @Override
    public void setTexture(Texture texture) {
        getShapes().forEach(shape -> shape.setTexture(texture));
    }

    @Override
    public int store(Matrix4f in, ByteBuffer buffer) {
        int cnt = 0;
        for (ClientShape shape : getShapes())
            cnt += shape.store(in, buffer);
        return cnt;
    }

    public static ClientModel clientDeserialize(Config config) {
        Openverse.logger().info("DESERIALIZING A MODEL");
        ClientModel res = new ClientModel();
        if (config.has("color")) {
            // todo set color
        }
        if (config.has("texture")) {
            res.setTexture(((ClientResources) Openverse.resources()).textures().entry(config.getString("texture")));
        }
        for (Config shapeCfg : config.getConfigListRequired("shapes")) {
            ClientShapeType shapeFac = (ClientShapeType) Openverse.resources().shapes().entry(shapeCfg.getStringRequired("type"));
            if (shapeFac != null)
                res.addShape(shapeFac.create(shapeCfg));
        }
        return res;
    }
}