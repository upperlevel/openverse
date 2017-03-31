package xyz.upperlevel.opencraft.client.resource.model.impl;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.resource.model.Shape;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.opencraft.physic.Box;
import xyz.upperlevel.ulge.util.Color;

public class Quad implements Shape {

    @Getter
    private Box box;

    @Getter
    private Texture texture;

    @Getter
    private Vertex[] vertices = new Vertex[QuadVertex.values().length];
    {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = QuadVertex.values()[i].create();
    }

    public Quad() {
    }

    public Quad(@NonNull Box box) {
        this.box = box;
    }

    public void setTexture(@NonNull Texture texture) {
        this.texture = texture;
    }

    public void setColor(@NonNull Color color) {
        for (Vertex vertex : vertices)
            vertex.setColor(color);
    }

    public Vertex getVertex(QuadVertex pos) {
        return vertices[pos.ordinal()];
    }
}
