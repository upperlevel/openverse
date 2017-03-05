package xyz.upperlevel.opencraft.server.world;

public interface BlockType {

    String getId();

    static BlockType create(String id) {
        return () -> id;
    }
}
