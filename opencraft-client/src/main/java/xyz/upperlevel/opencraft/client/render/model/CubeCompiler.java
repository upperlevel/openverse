package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.render.Rendering;
import xyz.upperlevel.opencraft.resource.model.impl.Cube;
import xyz.upperlevel.opencraft.resource.model.impl.CubeFace;
import xyz.upperlevel.opencraft.resource.model.impl.Quad;
import xyz.upperlevel.opencraft.physic.Box;

import java.nio.ByteBuffer;

public class CubeCompiler implements ModelCompiler<Cube> {

    public CubeCompiler() {
    }

    @Override
    public Class<Cube> getModel() {
        return Cube.class;
    }

    @Override
    public int getVerticesCount() {
        return 6 * Rendering.get()
                .models()
                .get(Quad.class)
                .getVerticesCount();
    }

    @Override
    public int getDataCount() {
        return getVerticesCount() * 7;
    }

    private int compile(CubeFace face, Matrix4f in, ByteBuffer out) {
        CubeFaceCompilerHelper pos = CubeFaceCompilerHelper.from(face.getPosition());

        Quad quad = face.getQuad();
        Vector3f size = BoxCompilerHelper.boxSize(quad.getBox());

        in = new Matrix4f(in)
                .translate(pos.getDirection().mul(size))
                .scale(size)
                .translate(.5f, .5f, .5f)
                .rotate(pos.getRotation())
                .translate(-.5f, -.5f, -.5f);

        Class<? extends Quad> c = quad.getClass();

        return Rendering.get()
                .models()
                .get((Class<Quad>)quad.getClass())
                .compile(quad, in, out);
    }

    @Override
    public int compile(Cube cube, Matrix4f in, ByteBuffer out) {
        Box box = cube.getBox();

        in = new Matrix4f(in)
                .translate(
                        BoxCompilerHelper
                                .boxSize(box)
                                .add(BoxCompilerHelper.boxPosition(box))
                );

        int vertices = 0;
        for (CubeFace face : cube.getFaces())
            vertices += compile(face, in, out);

        return vertices;
    }
}
