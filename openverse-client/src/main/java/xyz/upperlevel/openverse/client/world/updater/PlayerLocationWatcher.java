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
    @Setter
    @NonNull
    private LivingEntity handle;

    private double x, y, z;
    private double yaw, pitch;

    public PlayerLocationWatcher(LivingEntity watcher) {
        this.handle = watcher;
    }

    public void update() {
        Location loc = handle.getLocation();
        if (    loc.getX() != x ||
                loc.getY() != y ||
                loc.getZ() != z) {
            x = loc.getX();
            y = loc.getY();
            z = loc.getZ();

            Packet packet = new PlayerChangePositionPacket(x, y, z);
            OpenverseClient.get().getEndpoint().getConnection().send(Openverse.getChannel(), packet);
        }

        if (    loc.getYaw() != yaw ||
                loc.getPitch() != pitch) {
            yaw = loc.getYaw();
            pitch = loc.getPitch();

            Packet packet = new PlayerChangeLookPacket(yaw, pitch);
            OpenverseClient.get().getEndpoint().getConnection().send(Openverse.getChannel(), packet);
        }
    }
}
