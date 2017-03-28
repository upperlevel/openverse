package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.resource.model.CubeFace;
import xyz.upperlevel.opencraft.client.resource.model.CubeFacePosition;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

import java.nio.ByteBuffer;

public class CubeFaceCompiler implements ModelCompiler<CubeFace> {

    private static final CubeFaceCompiler g = new CubeFaceCompiler();

    @Override
    public Class<CubeFace> getModel() {
        return CubeFace.class;
    }

    @Override
    public int compile(CubeFace model, Matrix4f in, ByteBuffer out) {
        CubeFacePosition pos = model.getPosition();
        Box b = model.getBox();

        Vector3f sz = new Vector3f(
                (float) b.width,
                (float) b.height,
                (float) b.depth
        );

        in = new Matrix4f(in)
                .translate(pos.getDirection().mul(sz))
                .scale(sz)
                .translate(.5f, .5f, .5f)
                .rotate(pos.getRotation())
                .translate(-.5f, -.5f, -.5f);

        return 0;
    }

    public static CubeFaceCompiler g() {
        return g;
    }
}
