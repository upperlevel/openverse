package xyz.upperlevel.opencraft.common.world;

public interface BridgeBlockType {

    String getId();

    static BridgeBlockType create(String id) {
        return () -> id;
    }
}