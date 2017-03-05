package xyz.upperlevel.opencraft.server.world;

public interface CBlockType {

    String getId();

    static CBlockType create(String id) {
        return () -> id;
    }
}
