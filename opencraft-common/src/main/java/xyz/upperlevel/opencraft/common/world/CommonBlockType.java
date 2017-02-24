package xyz.upperlevel.opencraft.common.world;

public interface CommonBlockType {

    String getId();

    static CommonBlockType create(String id) {
        return () -> id;
    }
}