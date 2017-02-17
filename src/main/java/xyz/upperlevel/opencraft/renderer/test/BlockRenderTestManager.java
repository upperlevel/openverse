package xyz.upperlevel.opencraft.renderer.test;

import lombok.Getter;
import xyz.upperlevel.opencraft.renderer.WorldViewer;
import world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockRenderTestManager {

    @Getter
    private final List<BlockRenderTest> tests = new ArrayList<>();

    public BlockRenderTestManager(){
    }

    public void register(BlockRenderTest test) {
        Objects.requireNonNull(test, "Test cannot be null.");
        tests.add(test);
    }

    public boolean unregister(BlockRenderTest test) {
        return tests.remove(test);
    }

    public boolean canRender(WorldViewer viewer, Block block) {
        return tests.stream().allMatch(test -> test.canRender(viewer, block));
    }
}
