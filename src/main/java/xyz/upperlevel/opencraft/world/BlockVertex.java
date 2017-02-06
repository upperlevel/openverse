package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.ulge.util.Colors;

enum BlockVertexPosition {
    TOP_LEFT {
        @Override
        public BlockVertex create() {
            return new BlockVertex() {{
                setX(-1f);
                setY(1f);
            }};
        }
    },
    TOP_RIGHT {
        @Override
        public BlockVertex create() {
            return new BlockVertex() {{
                setX(1f);
                setY(1f);
                setU(1f);
            }};
        }
    },
    BOTTOM_LEFT {
        @Override
        public BlockVertex create() {
            return new BlockVertex() {{
                setX(-1f);
                setY(-1f);
                setV(1f);
            }};
        }
    },
    BOTTOM_RIGHT {
        @Override
        public BlockVertex create() {
            return new BlockVertex() {{
                setX(1f);
                setY(-1f);
                setU(1f);
                setV(1f);
            }};
        }
    };

    public abstract BlockVertex create();
}

public class BlockVertex {

    @Getter
    @Setter
    private float x, y, z;

    @Getter
    @Setter
    @NonNull
    private Color color = Colors.WHITE;

    @Getter
    @Setter
    private float u, v;
}