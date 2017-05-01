package xyz.upperlevel.openverse.network;

import xyz.upperlevel.hermes.PacketConverter;

import java.nio.ByteBuffer;

public class SendChunkPacketConverter implements PacketConverter<SendChunkPacket> {

    @Override
    public byte[] toData(SendChunkPacket packet) {
        ByteBuffer buffer = ByteBuffer.allocate(packet.getPacketSize());
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    // put all three coordinates in int
                    int id = x << 16 | y << 8 | z;
                    buffer.put
                }
            }
        }
    }

    @Override
    public SendChunkPacket toPacket(byte[] data) {
        String[][][] chunk = new String[16][16][16];

    }
}
