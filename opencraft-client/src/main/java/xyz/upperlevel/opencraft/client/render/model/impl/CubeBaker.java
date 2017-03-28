package xyz.upperlevel.opencraft.client.render.model;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.resource.model.impl.Cube;
import xyz.upperlevel.opencraft.client.resource.model.impl.CubeFace;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

import java.nio.ByteBuffer;

public class CubeCompiler implements ModelCompiler<Cube> {

    public CubeCompiler() {
    }

    @Override
    public Class<Cube> getModel() {
        return Cube.class;
    }

    @Override
    public int compile(Cube cube, Matrix4f in, ByteBuffer out) {
        Box b = cube.getBox();

        in = new Matrix4f(in)
                .translate(new Vector3f(
                        (float) b.width,
                        (float) b.height,
                        (float) b.depth
                ).add(
                        (float) b.x,
                        (float) b.y,
                        (float) b.z
                ));

        int v = 0;
        for (CubeFace f : cube.getFaces()) {

            v += f.compile(in, out);
        }

        return v;
    }
}
