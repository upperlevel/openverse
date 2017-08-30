package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.World;
import xyz.upperlevel.openverse.world.entity.Player;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

@Getter
public class ServerPlayer extends Player implements PacketListener {
    private final Connection connection;

    public ServerPlayer(String name, Connection connection) {
        super(name);
        this.connection = connection;
        Openverse.channel().register(this);
    }

    @Override
    public void setLocation(Location loc, boolean update) {
        super.setLocation(loc, update);
        if (update) {
            connection.send(Openverse.channel(), new PlayerChangeWorldPacket(loc.getWorld()));
            connection.send(Openverse.channel(), new PlayerChangePositionPacket(loc.getX(), loc.getY(), loc.getZ()));
            connection.send(Openverse.channel(), new PlayerChangeLookPacket(loc.getYaw(), loc.getPitch()));
        }
    }

    @Override
    public void setLocation(Location loc) {
        PlayerMoveEvent e = new PlayerMoveEvent(this, loc);
        Openverse.getEventManager().call(e);
        setLocation(loc, true);
    }

    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        if (connection.equals(conn)) {
            Location loc = getLocation();
            loc.setYaw(pkt.getYaw());
            loc.setPitch(pkt.getPitch());
            setLocation(loc, false);
            System.out.println("[Server] Received player look change to: " + pkt.getYaw() + " " + pkt.getPitch());
        }
    }

    @PacketHandler
    public void onPlayerChangePosition(Connection conn, PlayerChangePositionPacket pkt) {
        if (connection.equals(conn)) {
            Location loc = getLocation();
            loc.setX(pkt.getX());
            loc.setY(pkt.getY());
            loc.setZ(pkt.getZ());
            setLocation(loc, false);
            System.out.println("[Server] Received player position change to: " + pkt.getX() + " " + pkt.getY() + " " + pkt.getZ());
        }
    }
}
