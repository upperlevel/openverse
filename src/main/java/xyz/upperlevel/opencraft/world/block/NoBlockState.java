package xyz.upperlevel.opencraft.world.block;

import java.util.HashMap;
import java.util.Map;

public class NoBlockState extends BlockState {

    public NoBlockState(BlockId id) {
        super(id);
    }

    @Override
    public void load(Map<String, Object> data) {
        // nothing
    }

    @Override
    public Map<String, Object> save() {
        return new HashMap<>();
    }
}
