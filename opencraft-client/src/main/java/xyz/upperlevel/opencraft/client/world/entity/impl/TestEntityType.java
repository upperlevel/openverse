package xyz.upperlevel.opencraft.client.world.entity.impl;

import xyz.upperlevel.opencraft.client.world.entity.EntityType;

public final class TestEntityType implements EntityType {

    public static final TestEntityType get = new TestEntityType();

    @Override
    public String getId() {
        return "test_entity";
    }

    public static TestEntityType get() {
        return get;
    }
}
