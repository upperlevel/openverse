package xyz.upperlevel.openverse.client.render.compiler;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.openverse.client.render.Graphics;
import xyz.upperlevel.openverse.client.render.ShapeCompiler;
import xyz.upperlevel.openverse.client.resource.model.ClientCube;
import xyz.upperlevel.openverse.client.resource.model.CubeFace;
import xyz.upperlevel.openverse.client.resource.model.CubeFacePosition;
import xyz.upperlevel.openverse.client.resource.model.Vertex;
import xyz.upperlevel.openverse.physic.Box;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

public class CubeCompiler implements ShapeCompiler<ClientCube> {

    @Override
    public int getVerticesCount() {
        return 8;
    }

    @Override
    public int getDataCount() {
        return -1; // todo 8 * 4;
    }

    // todo cube face position in inner class! render != resource

    public int compile(CubeFace model, Matrix4f in, ByteBuffer out) {
        Vector3f size = model.getBox().getSize();
        CubeFacePosition position = model.getPosition();

        in = new Matrix4f(in)
                // put the face in the right cube position
                .translate(position.getDirection().mul(size))
                .scale(size)
                // rotates the face to its position
                .translate(.5f, .5f, .5f)
                .rotate(position.getRotation())
                .translate(-.5f, -.5f, -.5f);

        // retrieves texture layer from passed bakery
        int layer = Graphics
                .getTextureBakery()
                .getLayer(model.getTexture());

        for (Vertex v : model.getVertices()) {
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

            // tex coordinates
            out.putFloat(v.getU())
                    .putFloat(v.getV())
                    .putFloat((float) layer); // texture id
        }
        return 4;
    }

    @Override
    public int compile(ClientCube shape, Matrix4f in, ByteBuffer out) {
        Box box = shape.getBox();
        // moves the model part to its position related to block space
        in = new Matrix4f(in)
                .translate(
                        box.getSize()
                                .add(box.getPosition())
                );
        int vrt = 0;
        for (CubeFace face : shape.getFaces())
            vrt += compile(face, in, out);
        return vrt;
    }
}
