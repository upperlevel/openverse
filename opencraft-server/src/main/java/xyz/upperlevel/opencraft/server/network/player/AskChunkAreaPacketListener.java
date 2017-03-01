package xyz.upperlevel.opencraft.server.network.player;

import xyz.upperlevel.opencraft.common.network.SingleplayerServer;
import xyz.upperlevel.opencraft.common.network.packet.AskChunkAreaPacket;
import xyz.upperlevel.opencraft.common.network.packet.ChunkAreaPacket;
import xyz.upperlevel.opencraft.server.OpenCraftServer;
import xyz.upperlevel.utils.event.EventListener;

public class AskChunkAreaPacketListener extends EventListener<AskChunkAreaPacket> {

    public AskChunkAreaPacketListener() {
    }

    @Override
    public byte getPriority() {
        return 0;
    }

    @Override
    public void call(AskChunkAreaPacket packet) {
        int cx = packet.getX();
        int cy = packet.getY();
        int cz = packet.getZ();

        System.out.println("Server> Sent chunk at x=" + cx + " y=" + cy + " z=" + cz);
        SingleplayerServer.connection().sendPacket(new ChunkAreaPacket(
                cx, cy, cz,
                OpenCraftServer.get().getWorld()
                        .getChunk(cx, cy, cz)
                        .getArea())
        );
    }
}
