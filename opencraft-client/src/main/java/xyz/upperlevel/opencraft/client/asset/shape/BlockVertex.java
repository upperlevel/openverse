package xyz.upperlevel.opencraft.client.asset.shape;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.ulge.util.Color;

@Accessors(chain = true)
public class BlockVertex {

    public static final int VERTICES_COUNT = 1;

    public static final int DATA_COUNT = 3 + 4 + 2;

    @Getter
    @NonNull
    private BlockFace face;

    @Getter
    @Setter
    private float x, y, z;

    @Getter
    @Setter
    private float r, g, b, a;

    @Getter
    @Setter
    private float u, v;

    public BlockVertex(@NonNull BlockFace face) {
        this.face = face;

        setColor(Color.WHITE);
    }

    public Color getColor() {
        return Color.rgba(r, g, b, a);
    }

    public void setColor(Color color) {
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }
}