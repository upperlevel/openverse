package xyz.upperlevel.opencraft.client.network.player;

import xyz.upperlevel.opencraft.client.OpenCraftClient;
import xyz.upperlevel.opencraft.client.render.RenderArea;
import xyz.upperlevel.opencraft.client.render.RenderChunk;
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

        RenderArea ra = viewer.getRenderarea();

        int abs_x = packet.getX();
        int abs_y = packet.getY();
        int abs_z = packet.getZ();

        int rel_x = abs_x - ra.getCenterX() + RenderArea.RADIUS;
        int rel_y = abs_y - ra.getCenterY() + RenderArea.RADIUS;
        int rel_z = abs_z - ra.getCenterZ() + RenderArea.RADIUS;

        ra.setChunk(rel_x, rel_y, rel_z, new RenderChunk(ra, rel_x, rel_y, rel_z).load(packet.getArea()));
    }
}
