package xyz.upperlevel.openverse.world.entity.player;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.inventory.PlayerInventorySession;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.item.ItemType;
import xyz.upperlevel.openverse.network.inventory.PlayerCloseInventoryPacket;
import xyz.upperlevel.openverse.network.inventory.PlayerOpenInventoryPacket;
import xyz.upperlevel.openverse.network.world.PlayerBreakBlockPacket;
import xyz.upperlevel.openverse.network.world.PlayerUseItemPacket;
import xyz.upperlevel.openverse.world.Location;
import xyz.upperlevel.openverse.world.block.BlockFace;
import xyz.upperlevel.openverse.world.entity.EntityType;
import xyz.upperlevel.openverse.world.entity.LivingEntity;
import xyz.upperlevel.openverse.world.entity.player.events.PlayerInventoryCloseEvent;
import xyz.upperlevel.openverse.world.entity.player.events.PlayerInventoryOpenEvent;

import static xyz.upperlevel.openverse.world.chunk.storage.BlockStorage.AIR_STATE;

public class Player extends LivingEntity {
    public static final Type TYPE = new Type();
    @Getter
    private final String name;
    @Getter
    @Setter
    private Connection connection;
    @Getter
    @Setter
    private PlayerInventory inventory = new PlayerInventory();

    private PlayerInventorySession inventorySession;

    public Player(Location loc, String name) {
        super(TYPE, loc);
        this.name = name;
        setSize(0.6f, 1.8f);
    }

    public EntityType getType() {
        return TYPE;
    }

    public void breakBlock(int x, int y, int z, boolean sendPacket) {
        getWorld().setBlockState(x, y, z, AIR_STATE);
        if (sendPacket) {
            PlayerBreakBlockPacket packet = new PlayerBreakBlockPacket(x, y, z);
            Openverse.endpoint().getConnections().forEach(s -> s.send(Openverse.channel(), packet));
        }
    }

    public void breakBlock(int x, int y, int z) {
        breakBlock(x, y, z, true);
    }

    public boolean useItemInHand(int x, int y, int z, BlockFace face, boolean sendPacket) {
        boolean result = inventory
                .getHandItem()
                .getType()
                .onUseBlock(this, inventory.getHandItem(), x, y, z, face);

        if (sendPacket) {
            PlayerUseItemPacket packet = new PlayerUseItemPacket(x, y, z, face);
            Openverse.endpoint().getConnections().forEach(s -> s.send(Openverse.channel(), packet));
        }

        return result;
    }

    public boolean useItemInHand(int x, int y, int z, BlockFace face) {
        return useItemInHand(x, y, z, face, true);
    }

    /**
     * Opens an inventory without sending any packet.
     * <br>This may call the events {@link PlayerInventoryCloseEvent} and {@link PlayerInventoryOpenEvent}
     * @param inventory the inventory to open
     */
    public void openInventory(Inventory inventory) {
        if (inventorySession != null && inventorySession.getInventory() == inventory) return;
        if (inventorySession != null) {
            closeInventory();
        }
        Openverse.getEventManager().call(new PlayerInventoryOpenEvent(this, inventory));
        inventorySession = new PlayerInventorySession(this, inventory);
    }

    /**
     * Opens the player inventory on both endpoints.
     * <br>This method sends a packet to the other endpoint because it's seen as an input, use {@link #openInventory(Inventory)} otherwise
     * <br>This may call the events {@link PlayerInventoryCloseEvent} and {@link PlayerInventoryOpenEvent}
     */
    public void openInventory() {
        getConnection().send(Openverse.channel(), new PlayerOpenInventoryPacket());
        openInventory(inventory);
    }

    public Inventory getOpenedInventory() {
        return inventorySession == null ? null : inventorySession.getInventory();
    }

    public boolean hasOpenedInventory() {
        return inventorySession != null;
    }

    public PlayerInventorySession getInventorySession() {
        return inventorySession;
    }

    /**
     * Closes the inventory and notifies the endpoint.
     * <br>If you don't want to notify the endpoint use {@link #onCloseInventory()}.
     */
    public void closeInventory() {
        if (inventorySession == null) {
            Openverse.logger().warning("Trying to close a closed inventory");
            return;
        }
        getConnection().send(Openverse.channel(), new PlayerCloseInventoryPacket());
        onCloseInventory();
    }

    /**
     * Closes the inventory without sending any packet.
     * <br>Used when a the inventory needs to be closed without the other endpoint noticing.
     * <br> To notify the endpoint us4e {@link #closeInventory()}.
     */
    public void onCloseInventory() {
        if (inventorySession == null) {
            Openverse.logger().warning("Server trying to close a closed inventory");
            return;
        }
        inventorySession.onClose();
        Openverse.getEventManager().call(new PlayerInventoryCloseEvent(this, inventorySession.getInventory()));
        inventorySession = null;
    }

    @Override
    public double getEyeHeight() {
        return 1.75f;
    }

    public static class Type extends EntityType {

        public Type() {
            super("player");
        }

        @Override
        public Player spawn(Location loc) {
            throw new IllegalStateException("Cannot spawn player");
        }
    }
}
