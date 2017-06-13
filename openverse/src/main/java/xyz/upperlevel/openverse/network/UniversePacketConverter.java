package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UniversePacketConverter implements PacketConverter<UniversePacket> {

    // gets world names total byte size
    private int getSize(List<String> wn) {
        int i = 0;
        for (String n : wn)
            i += n.length() * Character.BYTES;
        return i;
    }

    public String readString(ByteBuffer buf) {
        StringBuilder out = new StringBuilder();
        char c;
        while ((c = buf.getChar()) != '\0')
            out.append(c);
        return out.toString();
    }

    @Override
    public byte[] toData(UniversePacket packet) {
        List<String> worlds = packet.getWorlds();

        ByteBuffer out = ByteBuffer.allocate(
                // spawn location
                packet.getSpawnWorld().length() * Character.BYTES +
                        Double.BYTES +
                        Double.BYTES +
                        Double.BYTES +

                        // worlds
                        getSize(worlds)
        );

        // spawn location
        out.put(packet.getSpawnWorld().getBytes(StandardCharsets.UTF_8))
           .putChar('\0');

        out.putDouble(packet.getSpawnX())
           .putDouble(packet.getSpawnY())
           .putDouble(packet.getSpawnZ());

        // worlds
        for (String world : worlds)
            out.put(world.getBytes(StandardCharsets.UTF_8))
               .putChar('\0');

        return out.array();
    }

    @Override
    public UniversePacket toPacket(byte[] data) {
        ByteBuffer in = ByteBuffer.wrap(data);

        // spawn location
        String worldSpawn = readString(in);
        double spawnX = in.getDouble();
        double spawnY = in.getDouble();
        double spawnZ = in.getDouble();

        // worlds
        List<String> worlds = new ArrayList<>();
        while (in.hasRemaining())
            worlds.add(readString(in));

        return new UniversePacket(
                worldSpawn,
                spawnX,
                spawnY,
                spawnZ,
                worlds
        );
    }
}
