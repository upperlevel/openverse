package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.opencraft.client.resource.texture.Texture;

<<<<<<< HEAD
=======
import java.nio.ByteBuffer;

>>>>>>> 06eecab13fbfd6cb7388c12be9d345d48be77c19
public class CubeFace {

    @Getter
    private Cube cube;

    @Getter
    private FacePosition position;

    @Getter
    @Setter
    private Texture texture;

    public CubeFace(@NonNull Cube cube, @NonNull FacePosition position) {
        this.cube = cube;
<<<<<<< HEAD
        this.position = position;
=======
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


        quad.compile(trans, buf);
        return 4;
>>>>>>> 06eecab13fbfd6cb7388c12be9d345d48be77c19
    }
}
