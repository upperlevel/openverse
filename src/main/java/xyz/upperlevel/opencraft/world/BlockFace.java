package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class BlockFace {

    public static final BlockFace
            UP      = new BlockFace(0, 1, 0),
            DOWN    = new BlockFace(0, -1, 0),
            LEFT    = new BlockFace(-1, 0, 0),
            RIGHT   = new BlockFace(1, 0, 0),
            BACK    = new BlockFace(0, 0, -1),
            FORWARD = new BlockFace(0, 0, 1);

    public static final List<BlockFace> FACES = Collections.unmodifiableList(new ArrayList<BlockFace>() {{
        add(UP);
        add(DOWN);
        add(LEFT);
        add(RIGHT);
        add(BACK);
        add(FORWARD);
    }});

    @Getter public final int offsetX, offsetY, offsetZ;

    public Location offset(Location location) {
        return location.add(location);
    }

    public static void around(Block block, Consumer<Block> action) {
        BlockFace.FACES.forEach(face -> action.accept(face.offset(block.location.copy()).getBlock()));
    }
}