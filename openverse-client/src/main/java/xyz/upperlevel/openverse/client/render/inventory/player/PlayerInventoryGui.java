package xyz.upperlevel.openverse.client.render.inventory.player;

import xyz.upperlevel.openverse.client.render.inventory.InventoryGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotContainerGui;
import xyz.upperlevel.openverse.client.render.inventory.SlotGui;
import xyz.upperlevel.openverse.inventory.Slot;
import xyz.upperlevel.openverse.world.entity.player.PlayerInventory;
import xyz.upperlevel.ulge.gui.GuiAlign;
import xyz.upperlevel.ulge.gui.GuiBackground;
import xyz.upperlevel.ulge.gui.GuiBounds;
import xyz.upperlevel.ulge.gui.GuiRenderer;
import xyz.upperlevel.ulge.opengl.texture.Texture2d;
import xyz.upperlevel.ulge.opengl.texture.TextureFormat;
import xyz.upperlevel.ulge.opengl.texture.TextureParameters;
import xyz.upperlevel.ulge.opengl.texture.loader.ImageContent;
import xyz.upperlevel.ulge.util.Color;
import xyz.upperlevel.ulge.window.Window;

import javax.imageio.ImageIO;

public class PlayerInventoryGui extends InventoryGui<PlayerInventory> {
    /**
     * Distance from every side of the selected slot to render the background
     */
    private static final int SELECTION_DISTANCE = 5;

    private static final Texture2d backgroundTexture;
    private static final Texture2d selectionTexture;

    private SlotContainerGui slotGui = new SlotContainerGui(9, 4);
    private SlotGui handSlot;
    private GuiBounds handSlotTexBounds;

    static {
        backgroundTexture = loadTexture("guis/player_gui.png");
        selectionTexture = loadTexture("guis/selection.png");
    }

    public PlayerInventoryGui(PlayerInventory handle) {
        super(handle);
        buildSlots(handle);
        slotGui.setAlign(GuiAlign.CENTER);
        slotGui.setOffset(0);
        slotGui.setBackground(GuiBackground.texture(backgroundTexture));
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

    public void recalcHandSlot() {
        PlayerInventory inventory = getHandle();
        int slotIndex = 9 * 3 + inventory.getHandSlot();
        handSlot = slotGui.getSlot(slotIndex);
        GuiBounds slotBounds = handSlot.getBounds();
        int distanceX = (int) ((slotGui.getWidth() / (float)slotGui.getRelWidthPixels()) * SELECTION_DISTANCE);
        int distanceY = (int) ((slotGui.getHeight() / (float)slotGui.getRelHeightPixels()) * SELECTION_DISTANCE);
        handSlotTexBounds = new GuiBounds(
                slotBounds.minX - distanceX,
                slotBounds.minY - distanceY,
                slotBounds.maxX + distanceX,
                slotBounds.maxY + distanceY
        );
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
        recalcHandSlot();
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

    @Override
    public void render() {
        super.render();
        GuiRenderer r = GuiRenderer.get();
        r.setColor(Color.WHITE);
        r.setTexture(selectionTexture);
        r.render(getWindow(), handSlotTexBounds);
    }

    private static Texture2d loadTexture(String path) {
        ImageContent content;
        try {
            content = new ImageContent(ImageIO.read(PlayerInventoryGui.class.getClassLoader().getResource(path)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Texture2d res = new Texture2d();
        res.loadImage(TextureFormat.RGBA, content);
        res.setup(TextureParameters.getDefault());
        return res;
    }
}
