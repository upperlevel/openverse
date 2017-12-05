package xyz.upperlevel.openverse.client.render.inventory.player;

import xyz.upperlevel.openverse.client.render.inventory.InventoryGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotContainerGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotGui;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;
import xyz.upperlevel.ulge.gui.GuiAlign;
import xyz.upperlevel.ulge.gui.GuiRenderer;
import xyz.upperlevel.ulge.opengl.texture.Texture2d;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.ulge.window.Window;

public class PlayerInventoryGui extends InventoryGui<PlayerInventory> {
    private SlotContainerGui slotGui = new SlotContainerGui(9, 4);

    public PlayerInventoryGui(PlayerInventory handle) {
        super(handle);
        buildSlots(handle);
        addChild(slotGui);
        setAlign(GuiAlign.CENTER);
        setOffset(30);
    }

    protected void buildSlots(PlayerInventory inv) {
        int w = slotGui.getHorizontalSlots();// 9
        int h = slotGui.getVerticalSlots();// 4
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                slotGui.setSlot(x, y, new SlotGui(inv.get(y * w + x)));
            }
        }
    }

    @Override
    public void renderCurrent() {
        GuiRenderer renderer = GuiRenderer.get();
        renderer.setColor(Color.GREEN);
        renderer.setTexture(Texture2d.NULL);
        renderer.render(getWindow(), getBounds());
    }

    // I'm hating inheritance :(

    @Override
    public void setWindow(Window window) {
        super.setWindow(window);
        onResize();
    }

    @Override
    public void reloadLayout() {
        onResize();
        super.reloadLayout();
    }

    @Override
    public void onResize() {
        Window w = getWindow();
        if (w != null) {
            setSize(getParent().getWidth() - (getOffsetLeft() + getOffsetRight()), getParent().getHeight() - (getOffsetTop() + getOffsetBottom()));
        }
        super.onResize();
    }
}
