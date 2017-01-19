package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class BlockRelative {

    public static final BlockRelative
            UP      = new BlockRelative(0, 1, 0),
            DOWN    = new BlockRelative(0, -1, 0),
            LEFT    = new BlockRelative(-1, 0, 0),
            RIGHT   = new BlockRelative(1, 0, 0),
            BACK    = new BlockRelative(0, 0, -1),
            FORWARD = new BlockRelative(0, 0, 1);

    public static final List<BlockRelative> FACES = Collections.unmodifiableList(new ArrayList<BlockRelative>() {{
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
        BlockRelative.FACES.forEach(face -> action.accept(face.offset(block.loc.copy()).getBlock()));
    }
}