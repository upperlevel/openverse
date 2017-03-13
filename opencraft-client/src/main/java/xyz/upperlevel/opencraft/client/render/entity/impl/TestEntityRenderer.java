package xyz.upperlevel.opencraft.client.render.entity.impl;

import xyz.upperlevel.opencraft.client.render.entity.EntityRenderer;
import xyz.upperlevel.opencraft.client.world.entity.Entity;
import xyz.upperlevel.opencraft.client.world.entity.impl.TestEntityType;

public class TestEntityRenderer implements EntityRenderer<TestEntityType> {

    @Override
    public TestEntityType getType() {
        return TestEntityType.get();
    }

    @Override
    public void draw(Entity<TestEntityType> entity) {
        // todo
    }
}
