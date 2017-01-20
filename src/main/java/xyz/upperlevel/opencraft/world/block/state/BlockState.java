package xyz.upperlevel.opencraft.world.block.state;

import lombok.*;
import xyz.upperlevel.opencraft.world.block.id.BlockId;
import xyz.upperlevel.opencraft.world.block.shape.BlockShape;

import java.util.HashMap;
import java.util.Map;

public class BlockState {

    public final BlockId id;

    @Getter
    @Setter
    @NonNull
    private BlockShape shape = BlockShape.empty();

    public BlockState(BlockId id) {
        this.id = id;
    }

    public BlockState(BlockId id, BlockShape shape) {
        this(id);
        this.shape = shape;
    }

    public final BlockId getId() {
        return id;
    }

    public void load(Map<String, Object> data) {
    }

    public Map<String, Object> save() {
        return new HashMap<>();
    }
}