package xyz.upperlevel.openverse;

import xyz.upperlevel.hermes.PacketSide;
import xyz.upperlevel.hermes.Protocol;
import xyz.upperlevel.openverse.console.ChatColor;
import xyz.upperlevel.openverse.network.LoginRequestPacket;
import xyz.upperlevel.openverse.network.LoginResponsePacket;
import xyz.upperlevel.openverse.network.inventory.*;
import xyz.upperlevel.openverse.network.world.ChunkCreatePacket;
import xyz.upperlevel.openverse.network.world.ChunkDestroyPacket;
import xyz.upperlevel.openverse.network.world.PlayerBreakBlockPacket;
import xyz.upperlevel.openverse.network.world.PlayerUseItemPacket;
import xyz.upperlevel.openverse.network.world.entity.*;
import xyz.upperlevel.openverse.network.world.registry.BlockRegistryPacket;
import xyz.upperlevel.openverse.network.world.registry.ItemRegistryPacket;
import xyz.upperlevel.openverse.world.chunk.HeightmapPacket;

import java.util.logging.LogRecord;

public final class Openverse {
    public static final Protocol PROTOCOL = Protocol.builder()
            .packet(PacketSide.SERVER, EntityRemovePacket.class)
            .packet(PacketSide.SERVER, EntitySpawnPacket.class)
            .packet(PacketSide.SERVER, EntityTeleportPacket.class)
            .packet(PacketSide.SERVER, ChunkCreatePacket.class)
            .packet(PacketSide.SERVER, ChunkDestroyPacket.class)
            .packet(PacketSide.SERVER, HeightmapPacket.class)
            .packet(PacketSide.SERVER, PlayerChangeWorldPacket.class)
            .packet(PacketSide.SERVER, LoginResponsePacket.class)
            .packet(PacketSide.SERVER, SlotChangePacket.class)
            .packet(PacketSide.SERVER, InventoryContentPacket.class)
            .packet(PacketSide.SERVER, BlockRegistryPacket.class)
            .packet(PacketSide.SERVER, ItemRegistryPacket.class)
            .packet(PacketSide.SERVER, EntityChangeVelocityPacket.class)
            .packet(PacketSide.SERVER, SlotChangePacket.class)
            .packet(PacketSide.SERVER, InventoryContentPacket.class)

            .packet(PacketSide.SHARED, PlayerOpenInventoryPacket.class)
            .packet(PacketSide.SHARED, PlayerCloseInventoryPacket.class)
            .packet(PacketSide.SHARED, PlayerChangeHandSlotPacket.class)
            .packet(PacketSide.SHARED, PlayerChangeLookPacket.class)
            .packet(PacketSide.SHARED, PlayerChangePositionPacket.class)
            .packet(PacketSide.SHARED, PlayerChangeLookPacket.class)
            .packet(PacketSide.SHARED, PlayerChangePositionPacket.class)
            .packet(PacketSide.SHARED, PlayerBreakBlockPacket.class)
            .packet(PacketSide.SHARED, PlayerUseItemPacket.class)

            .packet(PacketSide.CLIENT, LoginRequestPacket.class)
            .packet(PacketSide.CLIENT, PlayerInventoryActionPacket.class)
            .build();


    public static class Logger extends java.util.logging.Logger {
        protected Logger() {
            super(Openverse.class.getCanonicalName(), null);
        }

        @Override
        public void log(LogRecord log) {
            log.setMessage(ChatColor.MAGENTA + "[Core] " + log.getMessage());
            super.log(log); // todo dispatcher.queue(log);
        }
    }

    public static final Logger logger = new Logger();
}
