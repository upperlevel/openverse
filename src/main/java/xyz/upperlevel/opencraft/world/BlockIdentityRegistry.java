package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.world.block.BlockId;

import java.util.*;

public class BlockIdentityRegistry {

    public static final BlockIdentityRegistry $ = new BlockIdentityRegistry();

    private final Map<String, BlockId> ids = new HashMap<>();

    public void register(BlockId id) {
        ids.put(id.str, id);
    }

    public boolean unregister(BlockId id) {
        return ids.remove(id.str) != null;
    }

    public Optional<BlockId> getBlockId(String idStr) {
        return Optional.ofNullable(ids.get(idStr));
    }

    public Collection<BlockId> getBlockIds() {
        return Collections.unmodifiableCollection(ids.values());
    }

    public static BlockIdentityRegistry instance() {
        return $;
    }
}
