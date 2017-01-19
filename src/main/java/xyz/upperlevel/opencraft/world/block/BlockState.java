package xyz.upperlevel.opencraft.world.block;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.opencraft.world.block.BlockId;
import xyz.upperlevel.opencraft.world.util.Savable;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BlockState implements Savable {

    public final BlockId id;

    public final BlockId getId() {
        return id;
    }

    @Override
    public void load(Map<String, Object> data) {
    }

    @Override
    public Map<String, Object> save() {
        return new HashMap<>();
    }
}