package xyz.upperlevel.openverse.server.world.entity;

import lombok.Getter;
import org.joml.Vector3d;
import xyz.upperlevel.hermes.Packet;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.network.world.entity.EntityChangeVelocityPacket;
import xyz.upperlevel.openverse.network.world.entity.EntityTeleportPacket;
import xyz.upperlevel.openverse.server.OpenverseServer;
import xyz.upperlevel.openverse.server.world.chunk.PlayerChunkCache;
import xyz.upperlevel.openverse.server.world.chunk.PlayerChunkMapListener;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.chunk.ChunkMap;
import xyz.upperlevel.openverse.world.entity.Entity;
import xyz.upperlevel.openverse.world.entity.player.Player;

import java.util.Collection;
import java.util.Map;

public class EntityWatcher {
    @Getter
    private final OpenverseServer server;

    @Getter
    private final PlayerChunkMapListener map;
    private ChunkMap<Map<Entity, Data>> entities = new ChunkMap<>();

    public EntityWatcher(OpenverseServer server, PlayerChunkMapListener map) {
        this.server = server;

        this.map = map;
    }

    public void onTick() {
        for (PlayerChunkCache playerChunk : map.getChunks().getChunks()) {
            Map<Entity, Data> entities = this.entities.get(playerChunk.getLocation());
            for (Map.Entry<Entity, Data> entityData : entities.entrySet()) {
                entityData.getValue().update(entityData.getKey(), playerChunk.getPlayers());
            }
        }
    }


    protected static class Data {
        private final OpenverseServer server;

        public double posX, posY, posZ;
        public double yaw, pitch;
        public double velX, velY, velZ;

        public Data(OpenverseServer server) {
            this.server = server;
        }

        public void update(Entity entity, Collection<Player> viewers) {
            updateLoc(entity, viewers);
            updateVel(entity, viewers);
        }

        public void updateLoc(Entity entity, Collection<Player> viewers) {
            Location loc = entity.getLocation();
            if (    loc.getX() != posX ||
                    loc.getY() != posY ||
                    loc.getZ() != posZ ||
                    loc.getYaw() != yaw ||
                    loc.getPitch() != pitch) {
                posX = loc.getX();
                posY = loc.getY();
                posZ = loc.getZ();

                yaw = loc.getYaw();
                pitch = loc.getPitch();

                Packet tpPacket = new EntityTeleportPacket(entity.getId(), posX, posY, posZ, yaw, pitch);
                for (Player player : viewers) {
                    if (player != entity) {
                        player.getConnection().send(server.getChannel(), tpPacket);
                    }
                }
            }
        }

        public void updateVel(Entity entity, Collection<Player> viewers) {
            Vector3d vel = entity.getVelocity();
            if (    vel.x != velX ||
                    vel.y != velY ||
                    vel.z != velZ) {
                velX = vel.x;
                velY = vel.z;
                velZ = vel.z;

                Packet valPacket = new EntityChangeVelocityPacket(entity.getId(), velX, velY, velZ);
                for (Player player : viewers) {
                    if (player != entity) {
                        player.getConnection().send(server.getChannel(), valPacket);
                    }
                }
            }
        }
    }
}
