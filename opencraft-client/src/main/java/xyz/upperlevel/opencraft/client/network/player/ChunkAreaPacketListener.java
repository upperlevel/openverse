package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.client.OpenCraftClient;
import xyz.upperlevel.opencraft.client.render.LocalWorld;
import xyz.upperlevel.opencraft.client.render.LocalChunk;
import xyz.upperlevel.opencraft.client.render.WorldViewer;
import xyz.upperlevel.opencraft.server.network.packet.ChunkAreaPacket;
import xyz.upperlevel.utils.event.EventListener;

public class ChunkAreaPacketListener extends EventListener<ChunkAreaPacket> {

    public ChunkAreaPacketListener() {
    }

    @Override
    public byte getPriority() {
        return 0;
    }

    @Override
    public void call(ChunkAreaPacket packet) {
        WorldViewer viewer = OpenCraftClient.get().getViewer();

        LocalWorld w = viewer.getWorld();
        int cx = packet.getX();
        int cy = packet.getY();
        int cz = packet.getZ();

        LocalChunk c = new LocalChunk(w, cx, cy, cz);
        c.load(packet.getArea(), false);
        w.setChunk(cx, cy, cz, c);
    }
}
