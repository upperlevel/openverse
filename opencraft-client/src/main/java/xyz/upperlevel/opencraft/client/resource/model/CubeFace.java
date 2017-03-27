package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;
import xyz.upperlevel.opencraft.common.physic.collision.Box;
import xyz.upperlevel.ulge.util.Color;

import java.nio.ByteBuffer;

@Accessors(chain = true)
public class CubeFace implements ModelCompiler {

    public static final int VERTICES_COUNT = QuadVertex.VERTICES_COUNT * 4;

    public static final int DATA_COUNT = QuadVertex.DATA_COUNT * VERTICES_COUNT;

    @Getter
    private Cube cube;

    @Getter
    private CubeFacePosition position;

    @Getter
    private Box box;

    @Getter
    private Quad quad = new Quad();

    public CubeFace(Cube cube, CubeFacePosition pos) {
        this.cube = cube;
        position = pos;
        box = pos.getBox(cube.getBox());
    }

    public void setTexture(Texture texture) {
        quad.setTexture(texture);
    }

    public void setColor(Color color) {
        quad.setColor(color);
    }

    @Override
    public int compile(Matrix4f trans, ByteBuffer buf) {
        Box b = cube.getBox();

        Vector3f sz = new Vector3f(
                (float) b.width,
                (float) b.height,
                (float) b.depth
        );

        trans = new Matrix4f(trans)
                .translate(position.getDirection().mul(sz))
                .scale(sz)
                .translate(.5f, .5f, .5f)
                .rotate(position.getRotation())
                .translate(-.5f, -.5f, -.5f);

        quad.compile(trans, buf);
        return 4;
    }
}
