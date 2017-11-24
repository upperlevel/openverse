package xyz.upperlevel.openverse.world.entity.player;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.hermes.Connection;
import xyz.upperlevel.openverse.Openverse;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.openverse.item.ItemStack;
import xyz.upperlevel.openverse.item.ItemType;
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

    private Inventory openedInventory;

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
                .onUseBlock(this, x, y, z, face);

        if (sendPacket) {
            PlayerUseItemPacket packet = new PlayerUseItemPacket(x, y, z, face);
            Openverse.endpoint().getConnections().forEach(s -> s.send(Openverse.channel(), packet));
        }

        return result;
    }

    public boolean useItemInHand(int x, int y, int z, BlockFace face) {
        return useItemInHand(x, y, z, face, true);
    }

    public void openInventory(Inventory inventory) {
        if (inventory == openedInventory)
        if (openedInventory != null) {
            closeInventory();
        }
        Openverse.getEventManager().call(new PlayerInventoryOpenEvent(this, inventory));
        openedInventory = inventory;
    }

    public void openInventory() {
        openInventory(inventory);
    }

    public Inventory getOpenedInventory() {
        return openedInventory;
    }

    public void closeInventory() {
        if (openedInventory == null) {
            Openverse.logger().warning("Trying to close a closed inventory");
            return;
        }
        Openverse.getEventManager().call(new PlayerInventoryCloseEvent(this, openedInventory));
        openedInventory = null;
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
