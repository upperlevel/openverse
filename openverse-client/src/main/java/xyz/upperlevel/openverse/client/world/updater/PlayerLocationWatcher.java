package xyz.upperlevel.openverse.client.world.updater;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.client.OpenverseClient;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangeLookPacket;
import xyz.upperlevel.openverse.network.world.entity.PlayerChangePositionPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.entity.LivingEntity;


public class PlayerLocationWatcher {
    @Getter
    private final OpenverseClient client;

    @Getter
    @Setter
    @NonNull
    private LivingEntity watching;

    private double x, y, z;
    private double yaw, pitch;

    public PlayerLocationWatcher(OpenverseClient client, LivingEntity watching) {
        this.client = client;

        this.watching = watching;
    }

    public void update() {
        Location loc = watching.getLocation();
        if (    loc.getX() != x ||
                loc.getY() != y ||
                loc.getZ() != z) {
            x = loc.getX();
            y = loc.getY();
            z = loc.getZ();

            Packet packet = new PlayerChangePositionPacket(x, y, z);
            client.getEndpoint().getConnection().send(client.getChannel(), packet);
        }

        if (    loc.getYaw() != yaw ||
                loc.getPitch() != pitch) {
            yaw = loc.getYaw();
            pitch = loc.getPitch();

            Packet packet = new PlayerChangeLookPacket(yaw, pitch);
            client.getEndpoint().getConnection().send(client.getChannel(), packet);
        }
    }
}
