package xyz.upperlevel.openverse.client.render.inventory.player;

import xyz.upperlevel.openverse.client.render.inventory.InventoryGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotGui;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;
import xyz.upperlevel.ulge.gui.GuiBounds;
import xyz.upperlevel.ulge.gui.GuiRenderer;
import xyz.upperlevel.ulge.util.Color;

import java.util.Arrays;
import java.util.List;

public class PlayerInventoryGui extends InventoryGui<PlayerInventory> {
    private List<SlotGui> slots;

    public PlayerInventoryGui(PlayerInventory handle) {
        super(handle);
        slots = buildSlots(handle);
        setPosition(0.1, 0.1);
        setSize(0.8, 0.8);
    }

    protected List<SlotGui> buildSlots(PlayerInventory inv) {
        SlotGui[] slots = new SlotGui[inv.getSize()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new SlotGui(inv.get(i));
        }
        return Arrays.asList(slots);
    }

    @Override
    public void render(GuiBounds upperBounds) {
        GuiBounds bounds = upperBounds.insideRelative(getBounds());
        GuiRenderer renderer = GuiRenderer.get();
        renderer.setColor(Color.GREEN);
        renderer.render(bounds);
        for (SlotGui slot : slots) {
            slot.render(bounds);
        }
    }
}
