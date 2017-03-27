package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.opencraft.client.resource.texture.TextureBakery;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

import static xyz.upperlevel.opencraft.client.resource.model.QuadVertexBufferStorer.putColor;
import static xyz.upperlevel.opencraft.client.resource.model.QuadVertexBufferStorer.putPosition;
import static xyz.upperlevel.opencraft.client.resource.model.QuadVertexBufferStorer.putTextureCoordinates;

public class Quad implements ModelCompiler {

    @Getter
    @Setter
    private Texture texture;

    @Getter
    private QuadVertex[] vertices = new QuadVertex[4];

    {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = QuadVertexPosition.values()[i].create();
        }
    }

    public Quad() {
    }

    public void setColor(Color color) {
        for (QuadVertex v : vertices)
            v.setColor(color);
    }

    public QuadVertex getVertex(QuadVertexPosition pos) {
        return vertices[pos.ordinal()];
    }

    @Override
    public int compile(Matrix4f trans, ByteBuffer buf) {
        int texLayer = TextureBakery.SIMPLE_INST.getId(texture);

        QuadVertex v;
        
        // top left
        v = getVertex(QuadVertexPosition.TOP_LEFT);
        putPosition(buf, trans.transformPosition(v.getX(), v.getY(), 0f, new Vector3f()));
        putColor(buf, v.getColor());
        putTextureCoordinates(buf, v.getU(), v.getV(), texLayer);

        // bottom left
        v = getVertex(QuadVertexPosition.BOTTOM_LEFT);
        putPosition(buf, trans.transformPosition(v.getX(), v.getY(), 0f, new Vector3f()));
        putColor(buf, v.getColor());
        putTextureCoordinates(buf, v.getU(), v.getV(), texLayer);

        // bottom right
        v = getVertex(QuadVertexPosition.BOTTOM_RIGHT);
        putPosition(buf, trans.transformPosition(v.getX(), v.getY(), 0f, new Vector3f()));
        putColor(buf, v.getColor());
        putTextureCoordinates(buf, v.getU(), v.getV(), texLayer);

        // top right
        v = getVertex(QuadVertexPosition.TOP_RIGHT);
        putPosition(buf, trans.transformPosition(v.getX(), v.getY(), 0f, new Vector3f()));
        putColor(buf, v.getColor());
        putTextureCoordinates(buf, v.getU(), v.getV(), texLayer);

        return 4;
    }
}
