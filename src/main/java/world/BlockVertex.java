package world;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.upperlevel.ulge.util.Color;

@RequiredArgsConstructor
@Accessors(chain = true)
public final class BlockVertex {

    public static final int SIZE = 3 + 4 + 2;

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