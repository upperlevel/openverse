package xyz.upperlevel.openverse.client.render.inventory.player;

import xyz.upperlevel.openverse.client.render.inventory.InventoryGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotContainerGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotGui;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;
import xyz.upperlevel.ulge.gui.GuiAlign;
import xyz.upperlevel.ulge.gui.GuiBackground;
import xyz.upperlevel.ulge.opengl.texture.Texture2d;
import xyz.upperlevel.ulge.opengl.texture.TextureFormat;
import xyz.upperlevel.ulge.opengl.texture.TextureParameters;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.window.Window;

import javax.imageio.ImageIO;

public class PlayerInventoryGui extends InventoryGui<PlayerInventory> {
    private static final Texture2d texture;
    private SlotContainerGui slotGui = new SlotContainerGui(9, 4);

    static {
        ImageContent content;
        try {
            content = new ImageContent(ImageIO.read(PlayerInventoryGui.class.getClassLoader().getResource("guis/player_gui.png")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        texture = new Texture2d();
        texture.loadImage(TextureFormat.RGBA, content);
        texture.setup(TextureParameters.getDefault());
    }

    public PlayerInventoryGui(PlayerInventory handle) {
        super(handle);
        buildSlots(handle);
        slotGui.setAlign(GuiAlign.CENTER);
        slotGui.setOffset(0);
        slotGui.setBackground(GuiBackground.texture(texture));
        addChild(slotGui);
        setAlign(GuiAlign.CENTER);
        setOffset(30);
    }

    protected void buildSlots(PlayerInventory inv) {
        int w = slotGui.getHorizontalSlots();
        int h = slotGui.getVerticalSlots();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                slotGui.setSlot(x, y, new SlotGui(inv.get(y * w + x)));
            }
        }
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
            int width = (getWidth() - (slotGui.getOffsetLeft() + slotGui.getOffsetRight())) / slotGui.getHorizontalSlots();
            int height = (getHeight() - (slotGui.getOffsetTop() + slotGui.getOffsetBottom())) / slotGui.getVerticalSlots();
            int value = Math.min(width, height);
            slotGui.setSize(value * slotGui.getHorizontalSlots(), value * slotGui.getVerticalSlots());
        }
        super.onResize();
    }
}
