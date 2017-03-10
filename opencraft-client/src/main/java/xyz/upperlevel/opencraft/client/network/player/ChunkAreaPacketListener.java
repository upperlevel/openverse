package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.client.OpenCraftClient;
import xyz.upperlevel.opencraft.client.render.ViewRenderer;
import xyz.upperlevel.opencraft.client.render.ChunkRenderer;
import xyz.upperlevel.opencraft.client.render.ViewerRenderer;
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
        ViewerRenderer viewer = OpenCraftClient.get().getViewer();

        ViewRenderer ra = viewer.getView();

        int abs_x = packet.getX();
        int abs_y = packet.getY();
        int abs_z = packet.getZ();

        int rel_x = abs_x - ra.getCenterX() + ViewRenderer.RADIUS;
        int rel_y = abs_y - ra.getCenterY() + ViewRenderer.RADIUS;
        int rel_z = abs_z - ra.getCenterZ() + ViewRenderer.RADIUS;

        ChunkRenderer rc = new ChunkRenderer(ra, rel_x, rel_y, rel_z);
        rc.load(packet.getArea(), false);
        ra.setChunk(rel_x, rel_y, rel_z, rc);
    }
}
