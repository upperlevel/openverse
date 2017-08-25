package xyz.upperlevel.openverse.network;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.world.Universe;

import java.util.ArrayList;
import java.util.List;

import static xyz.upperlevel.openverse.network.SerialUtil.readString;
import static xyz.upperlevel.openverse.network.SerialUtil.writeString;

@NoArgsConstructor
public class UniversePacket implements Packet {

    @Getter
    private List<String> worlds;

    public UniversePacket(@NonNull List<String> worlds) {
        this.worlds = worlds;
    }

    public UniversePacket(@NonNull Universe universe) {
        worlds = new ArrayList<>(universe.getWorldMap().keySet());
    }

    @Override
    public void toData(ByteBuf out) {
        for (String world : worlds)
            writeString(world, out);
    }

    @Override
    public void fromData(ByteBuf in) {
        worlds = new ArrayList<>(4);
        while (in.readableBytes() > 0)
            worlds.add(readString(in));
    }
}
