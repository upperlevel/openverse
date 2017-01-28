package xyz.upperlevel.opencraft.event;

import lombok.*;
import xyz.upperlevel.opencraft.util.Event;
import xyz.upperlevel.opencraft.world.Block;
import xyz.upperlevel.opencraft.world.block.BlockState;

@AllArgsConstructor
public class BlockSetEvent implements Event {

    @Getter
    public final Block block;

    @Getter
    private final BlockState oldBlockState;

    @Getter
    @Setter
    @NonNull
    private BlockState newBlockState;
}
