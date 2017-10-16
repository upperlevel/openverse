package xyz.upperlevel.openverse;

import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.EventManager;
import xyz.upperlevel.hermes.Endpoint;
import xyz.upperlevel.hermes.PacketSide;
import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.hermes.channel.Channel;
import xyz.upperlevel.openverse.network.LoginRequestPacket;
import xyz.upperlevel.openverse.network.LoginResponsePacket;
import xyz.upperlevel.openverse.network.world.entity.*;
import xyz.upperlevel.openverse.network.world.*;
import xyz.upperlevel.openverse.resource.Resources;
import xyz.upperlevel.openverse.world.entity.EntityManager;

import java.util.logging.Logger;

@RequiredArgsConstructor
public final class Openverse {
    public static final Protocol PROTOCOL = Protocol.builder()
            .packet(PacketSide.SERVER, EntityRemovePacket.class)
            .packet(PacketSide.SERVER, EntitySpawnPacket.class)
            .packet(PacketSide.SERVER, EntityTeleportPacket.class)
            .packet(PacketSide.SERVER, ChunkCreatePacket.class)
            .packet(PacketSide.SERVER, ChunkDestroyPacket.class)
            .packet(PacketSide.SHARED, PlayerChangeLookPacket.class)
            .packet(PacketSide.SHARED, PlayerChangePositionPacket.class)
            .packet(PacketSide.SERVER, PlayerChangeWorldPacket.class)
            .packet(PacketSide.CLIENT, LoginRequestPacket.class)
            .packet(PacketSide.SERVER, LoginResponsePacket.class)
            .packet(PacketSide.SERVER, BlockRegistryPacket.class)
            .build();

    private static OpenverseProxy proxy;

    public static OpenverseProxy getProxy() {
        return proxy;
    }

    public static void setProxy(OpenverseProxy proxy) {
        Openverse.proxy = proxy;
    }

    public static EventManager getEventManager() {
        return proxy.getEventManager();
    }

    public static EntityManager entities() {
        return proxy.getEntityManager();
    }

    public static Logger logger() {
        return proxy.getLogger();
    }

    public static Resources resources() {
        return proxy.getResources();
    }

    public static Endpoint endpoint() {
        return proxy.getEndpoint();
    }

    public static Channel channel() {
        return proxy.getChannel();
    }
}
