package xyz.upperlevel.opencraft.world;

import lombok.*;
import xyz.upperlevel.graphicengine.api.util.Color;
import xyz.upperlevel.opencraft.Material;

@RequiredArgsConstructor
public class BlockData {


    @Getter @Setter @NonNull private Material material;
    @Getter @Setter @NonNull private Color color;

    @Getter @Setter(AccessLevel.PACKAGE) private int id = -1;

    @Getter public boolean transparent = false;

    public static final BlockData EMPTY = new BlockData(new Material(), Color.WHITE) {{
        transparent = true;
    }};
}
