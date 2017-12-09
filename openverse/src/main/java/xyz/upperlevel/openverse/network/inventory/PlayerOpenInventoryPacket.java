package xyz.upperlevel.openverse.network.inventory;

import io.netty.buffer.ByteBuf;
import xyz.upperlevel.hermes.Packet;

/**
 * Makes the player open his inventory.
 * <br>Doesn't really have any data to pass...
 */
public class PlayerOpenInventoryPacket implements Packet {
    @Override
    public void toData(ByteBuf out) {
    }

    @Override
    public void fromData(ByteBuf in) {
    }
}
