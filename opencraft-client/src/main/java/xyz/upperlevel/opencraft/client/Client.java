package xyz.upperlevel.opencraft.client;

import xyz.upperlevel.opencraft.common.network.SingleplayerClient;
import xyz.upperlevel.opencraft.client.render.RenderArea;
import xyz.upperlevel.opencraft.client.render.RenderChunk;
import xyz.upperlevel.opencraft.common.network.packet.ChunkPacket;
import xyz.upperlevel.opencraft.common.network.packet.PlayerTeleportPacket;
import xyz.upperlevel.opencraft.common.world.CommonChunk;
import xyz.upperlevel.ulge.opengl.shader.Program;
import xyz.upperlevel.ulge.opengl.shader.ShaderType;
import xyz.upperlevel.ulge.opengl.shader.ShaderUtil;
import xyz.upperlevel.ulge.opengl.shader.Uniformer;
import xyz.upperlevel.utils.event.EventListener;

import static xyz.upperlevel.opencraft.client.render.RenderArea.SIDE;

public final class Client {

    public static final Program program = new Program();
    public static final Uniformer programUni;
    static {
        ClassLoader cl = Client.class.getClassLoader();
        program.attach(ShaderUtil.load(ShaderType.VERTEX, cl.getResourceAsStream("shaders/simple_shader.vs")));
        program.attach(ShaderUtil.load(ShaderType.FRAGMENT, cl.getResourceAsStream("shaders/simple_shader.fs")));
        programUni = program.bind();
    }

    public static final WorldViewer viewer = new WorldViewer();

    static {
        SingleplayerClient.connection().getListenerManager().ifPresent(manager -> {
            manager.register(PlayerTeleportPacket.class, new EventListener<PlayerTeleportPacket>() {
                @Override
                public byte getPriority() {
                    return 0;
                }

                @Override
                public void call(PlayerTeleportPacket event) {
                    viewer.teleport(
                            event.getX(),
                            event.getY(),
                            event.getZ(),
                            event.getYaw(),
                            event.getPitch(),
                            false
                    );
                }
            });
            manager.register(ChunkPacket.class, new EventListener<ChunkPacket>() {
                @Override
                public byte getPriority() {
                    return 0;
                }

                @Override
                public void call(ChunkPacket event) {
                    CommonChunk c = event.getChunk();
                    System.out.println("Client->Receiving chunk from server at x=" + c.getX() + " y=" + c.getY() + " z=" + c.getZ());

                    int dcx = viewer.getChunkX() - c.getX() + RenderArea.DISTANCE;
                    int dcy = viewer.getChunkY() - c.getY() + RenderArea.DISTANCE;
                    int dcz = viewer.getChunkZ() - c.getZ() + RenderArea.DISTANCE;

                    if (dcx < 0 || dcx >= SIDE ||
                        dcy < 0 || dcy >= SIDE ||
                        dcz < 0 || dcz >= SIDE) {
                        return; // the chunk is not in the render distance
                    }

                    viewer.getRenderArea().setRenderChunk(dcx, dcy, dcz, new RenderChunk());
                }
            });
        });
    }

    private Client() {
    }

    public static WorldViewer viewer() {
        return viewer;
    }
}