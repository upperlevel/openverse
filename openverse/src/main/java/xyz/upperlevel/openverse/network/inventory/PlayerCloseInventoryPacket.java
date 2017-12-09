package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import xyz.upperlevel.hermes.Packet;

/**
 * Closes the player's inventory.
 * <br>Doesn't really have any data to pass...
 */
public class PlayerCloseInventoryPacket implements Packet {
    @Override
    public void toData(ByteBuf out) {
    }

    @Override
    public void fromData(ByteBuf in) {
    }
}
