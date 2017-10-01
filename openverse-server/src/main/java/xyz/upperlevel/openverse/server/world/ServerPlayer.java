package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.network.world.PlayerChangeWorldPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.player.Player;
import xyz.upperlevel.openverse.world.entity.event.PlayerMoveEvent;

@Getter
public class ServerPlayer extends Player implements PacketListener {
    private final Connection connection;

    public ServerPlayer(Location loc, String name, Connection connection) {
        super(loc, name);
        this.connection = connection;
        Openverse.channel().register(this);
    }

    @Override
    public void setLocation(Location loc, boolean update) {
        if (loc == null)
            throw new IllegalArgumentException("Invalid player location");
        PlayerMoveEvent e = new PlayerMoveEvent(this, loc);
        Openverse.getEventManager().call(e);
        if (update) {
            if (this.location != null && loc.getWorld() != this.location.getWorld()) {
                connection.send(Openverse.channel(), new PlayerChangeWorldPacket(loc.getWorld()));
            }
            connection.send(Openverse.channel(), new PlayerChangePositionPacket(loc.getX(), loc.getY(), loc.getZ()));
            connection.send(Openverse.channel(), new PlayerChangeLookPacket(loc.getYaw(), loc.getPitch()));
        }
        this.location = loc;
    }

    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        if (connection.equals(conn)) {
            Location loc = getLocation();
            loc.setYaw(pkt.getYaw());
            loc.setPitch(pkt.getPitch());
            setLocation(loc, false);
        }
    }

    @PacketHandler
    public void onPlayerChangePosition(Connection conn, PlayerChangePositionPacket pkt) {
        if (connection.equals(conn)) {
            Location loc = getLocation();
            loc.set(pkt.getX(), pkt.getY(), pkt.getZ());
            setLocation(loc, false);
        }
    }
}
