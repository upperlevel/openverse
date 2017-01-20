package xyz.upperlevel.opencraft.world;

import xyz.upperlevel.opencraft.world.block.BlockId;

import java.util.*;

public class BlockIdentityRegistry {

    public static final BlockIdentityRegistry $ = new BlockIdentityRegistry();

    private final Map<String, BlockId> ids = new HashMap<>();

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
