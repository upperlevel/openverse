package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.client.Openverse;
import xyz.upperlevel.opencraft.client.view.WorldView;
import xyz.upperlevel.opencraft.client.view.ChunkView;
import xyz.upperlevel.opencraft.client.view.WorldViewer;
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
        WorldViewer viewer = Openverse.get().getViewer();

        WorldView w = viewer.getWorld();
        int cx = packet.getX();
        int cy = packet.getY();
        int cz = packet.getZ();

        ChunkView c = new ChunkView(w, cx, cy, cz);
        c.load(packet.getArea(), false);
        w.setChunk(cx, cy, cz, c);
    }
}
