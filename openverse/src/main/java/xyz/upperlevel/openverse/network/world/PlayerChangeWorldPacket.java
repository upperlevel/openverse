package xyz.upperlevel.openverse.network.world;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.World;

import static xyz.upperlevel.openverse.network.SerialUtil.readString;
import static xyz.upperlevel.openverse.network.SerialUtil.writeString;

/**
 * This packet is sent from the server to the client when a player have to change world.
 */
@Getter
@NoArgsConstructor
public class PlayerChangeWorldPacket implements Packet {
    private String worldName;

    public PlayerChangeWorldPacket(World world) {
        this.worldName = world.getName();
    }

    @Override
    public void toData(ByteBuf out) {
        writeString(worldName, out);
    }

    @Override
    public void fromData(ByteBuf in) {
        this.worldName = readString(in);
    }
}
