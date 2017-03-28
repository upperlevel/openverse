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

public class Quad {

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
}
