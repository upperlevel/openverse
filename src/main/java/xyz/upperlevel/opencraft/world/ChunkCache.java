package xyz.upperlevel.opencraft.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static xyz.upperlevel.opencraft.world.Chunk.*;

@RequiredArgsConstructor
public class ChunkCache implements BlockArea<Block> {

    @Getter public final Chunk chunk;
    @Getter public final BlockData[][][] blocks = new BlockData[WIDTH][HEIGHT][LENGTH];

    @Getter final Set<Block> renderer = new HashSet<>();

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getLength() {
        return LENGTH;
    }

    private Location getLocation(int x, int y, int z) {
        return new Location(chunk.world, chunk.asWorldX(x), chunk.asWorldY(y), chunk.asWorldZ(z));
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return new Block(getLocation(x, y, z));
    }
}