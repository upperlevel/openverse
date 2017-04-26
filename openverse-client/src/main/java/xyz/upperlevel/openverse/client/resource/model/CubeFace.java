package xyz.upperlevel.openverse.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.client.render.Rendering;
import xyz.upperlevel.openverse.client.resource.Texture;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

import static xyz.upperlevel.openverse.client.resource.model.FaceVertexPosition.values;

public class CubeFace implements ClientModelPart {

    @Getter
    @NonNull
    private final ClientCube cube;

    @Getter
    @NonNull
    private final CubeFacePosition position;

    @Getter
    private final Box box;

    @Getter
    private final Vertex[] vertices = new Vertex[values().length];

    {
        for (int i = 0; i < vertices.length; i++)
            vertices[i] = values()[i].create(); // generates vertex in base of position
    }

    @Getter
    @Setter
    @NonNull
    private Texture texture;

    @Getter
    private final Vector3f size;

    public CubeFace(ClientCube cube, CubeFacePosition position) {
        this.cube = cube;
        this.position = position;
        this.box = position.getBox(cube.getBox());

        size = new Vector3f(
                (float) box.width,
                (float) box.height,
                (float) box.depth
        );
    }

    // sets color to all vertices
    public void setColor(Color color) {
        for (Vertex v : vertices)
            v.setColor(color);
    }

    @Override
    public int getVerticesCount() {
        return 4;
    }

    @Override
    public int getDataCount() {
        return -1; // todo
    }

    @Override
    public int compile(Matrix4f in, ByteBuffer out) {
        in = new Matrix4f(in)
                // put the face in the right cube position
                .translate(position.getDirection().mul(size))
                .scale(size)
                // rotates the face to its position
                .translate(.5f, .5f, .5f)
                .rotate(position.getRotation())
                .translate(-.5f, -.5f, -.5f);

        int tex = Rendering.get()
                .textures()
                .getId(texture);

        for (Vertex v : vertices) {
            // position
            Vector3f p = in.transformPosition(v.getX(), v.getY(), v.getZ(), new Vector3f());
            out.putFloat(p.x)
                    .putFloat(p.y)
                    .putFloat(p.z);

            // color
            Color c = v.getColor();
            out.putFloat(c.r)
                    .putFloat(c.g)
                    .putFloat(c.b)
                    .putFloat(c.a);

            // tex coords
            out.putFloat(v.getU())
                    .putFloat(v.getV())
                    .putFloat((float) tex); // texture id
        }
        return 0;
    }
}
