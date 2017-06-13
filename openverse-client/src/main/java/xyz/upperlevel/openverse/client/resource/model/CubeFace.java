package xyz.upperlevel.openverse.client.resource.model;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.openverse.client.resource.Texture;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.ulge.util.Color;

import static xyz.upperlevel.openverse.client.resource.model.FaceVertexPosition.values;

@Getter
@Setter
public class CubeFace implements ClientShape {

    private final ClientCube cube;
    private final CubeFacePosition position;
    private final Box box;
    private final Vertex[] vertices = new Vertex[values().length];
    {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = values()[i].create(); // generates vertex in base of position
    }

    private Texture texture;

    public CubeFace(ClientCube cube, CubeFacePosition position) {
        this.cube = cube;
        this.position = position;
        this.box = position.getBox(cube.getBox());
    }

    // sets color to all vertices
    public void setColor(Color color) {
        for (Vertex v : vertices)
            v.setColor(color);
    }
}
