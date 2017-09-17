package xyz.upperlevel.openverse.client.render.block;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class BlockModel {
    private Set<BlockPart> parts = new HashSet<>();

    public void addPart(BlockPart part) {
        parts.add(part);
    }

    public void removePart(BlockPart part) {
        parts.remove(part);
    }
}