package xyz.upperlevel.openverse.server.world;

import lombok.Getter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.hermes.reflect.PacketHandler;
import xyz.upperlevel.hermes.reflect.PacketListener;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.inventory.InventoryContentPacket;
import xyz.upperlevel.openverse.network.inventory.SlotChangePacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangePositionPacket;
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
        getInventory().addListener((inventory, slot) -> {
            SlotChangePacket packet = new SlotChangePacket(inventory.getId(), slot.getId(), slot.getContent());
            Openverse.endpoint().getConnections().forEach(c -> c.send(Openverse.channel(), packet));
        });
    }

    public void updateInventory() {
        connection.send(Openverse.channel(), new InventoryContentPacket(getInventory()));
    }

    @Override
    public void setLocation(Location loc) {
        if (loc == null)
            throw new IllegalArgumentException("Invalid player location");
        PlayerMoveEvent e = new PlayerMoveEvent(this, loc);
        Openverse.getEventManager().call(e);
        this.location = loc;
    }

    @PacketHandler
    public void onPlayerChangeLook(Connection conn, PlayerChangeLookPacket pkt) {
        if (connection.equals(conn)) {
            Location loc = getLocation();
            loc.setYaw(pkt.getYaw());
            loc.setPitch(pkt.getPitch());
            setLocation(loc);
        }
    }

    @PacketHandler
    public void onPlayerChangePosition(Connection conn, PlayerChangePositionPacket pkt) {
        if (connection.equals(conn)) {
            Location loc = getLocation();
            loc.set(pkt.getX(), pkt.getY(), pkt.getZ());
            setLocation(loc);
        }
    }
}
