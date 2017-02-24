package xyz.upperlevel.opencraft.server.world;

import xyz.upperlevel.opencraft.client.block.BlockShape;

public interface BlockType {

    String getId();

    BlockShape getShape();
}