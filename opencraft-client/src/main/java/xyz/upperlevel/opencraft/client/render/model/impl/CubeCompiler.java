package xyz.upperlevel.opencraft.client.render.model.impl;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.render.RenderContext;
import xyz.upperlevel.opencraft.client.render.model.CompilerHelper;
import xyz.upperlevel.opencraft.client.render.model.Compiler;
import xyz.upperlevel.opencraft.client.resource.model.impl.Cube;
import xyz.upperlevel.opencraft.client.resource.model.impl.CubeFace;
import xyz.upperlevel.opencraft.client.resource.model.impl.Quad;
import xyz.upperlevel.opencraft.physic.Box;

import java.nio.ByteBuffer;

public class CubeCompiler implements Compiler<Cube> {

    public CubeCompiler() {
    }

    @Override
    public Class<Cube> getModelClass() {
        return Cube.class;
    }

    private int bake(CubeFace face, RenderContext bakery, Matrix4f in, ByteBuffer out) {
        CubeFaceCompiler pos = CubeFaceCompiler.from(face.getPosition());

        Quad quad = face.getQuad();
        Vector3f size = CompilerHelper.boxSize(quad.getBox());

        // copy and modify matrix
        in = new Matrix4f(in)
                .translate(pos.getDir().mul(size))
                .scale(size)
                .translate(.5f, .5f, .5f)
                .rotate(pos.getRot())
                .translate(-.5f, -.5f, -.5f);

        return bakery.getModelBakery().bake(quad, bakery, in, out);
    }

    @Override
    public int bake(Cube cube, RenderContext bakery, Matrix4f in, ByteBuffer out) {
        Box box = cube.getBox();

        in = new Matrix4f(in)
                .translate(
                        CompilerHelper
                                .boxSize(box)
                                .add(CompilerHelper.boxPosition(box))
        );

        int vrt = 0;
        for (CubeFace f : cube.getFaces())
            vrt += bake(f, bakery, in, out);

        return vrt;
    }
}
