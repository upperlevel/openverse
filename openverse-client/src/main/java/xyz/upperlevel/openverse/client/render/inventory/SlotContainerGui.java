package xyz.upperlevel.openverse.client.render.inventory;

import lombok.Getter;
import lombok.Setter;
import xyz.upperlevel.ulge.gui.Gui;

public class SlotContainerGui extends Gui {
    private SlotGui[] slots;

    @Getter
    private final int horizontalSlots;
    @Getter
    private final int verticalSlots;
    @Getter
    @Setter
    private int slotPadding = 10;

    // Layout dependant classes!
    /**
     * The number of pixels of the whole gui in a "block-dependant reference", where every block's pixels is 16x16.
     * @return total pixels in the gui (padding and slots)
     */
    @Getter
    private final int relWidthPixels, relHeightPixels;

    public SlotContainerGui(int horizontalSlots, int verticalSlots) {
        this.horizontalSlots = horizontalSlots;
        this.verticalSlots = verticalSlots;
        slots = new SlotGui[horizontalSlots * verticalSlots];
        this.relWidthPixels = slotPadding + (16 + slotPadding) * horizontalSlots;
        this.relHeightPixels = slotPadding + (16 + slotPadding) * verticalSlots;
    }


    @Override
    public void reloadLayout(int parX, int parY, int parW, int parH) {
        super.reloadLayout(parX, parY, parW, parH);

        // Get the ratio between texture coords (16x16 pixels every gui) and screen coords
        float widthRatio = getWidth() / (float) relWidthPixels;
        float heightRatio = getHeight() / (float) relHeightPixels;

        final float slotWidth = 16 * widthRatio;
        final float slotHeight = 16 * heightRatio;
        final float widthPadding = slotPadding * widthRatio;
        final float heightPadding = slotPadding * heightRatio;

        // Left here for debugging purposes
        /*System.out.println("PARE SIZE: {" + parX + ", " + parY + ", " + parW + ", " + parH + "}");
        System.out.println("THIS SIZE: {" + getBounds() + "} -> w: " + getWidth() + ", h: " + getHeight());
        System.out.println("SLOT SIZE: {" + slotWidth + ", " + slotHeight + "}");*/
        for (SlotGui slot : slots) {
            if (slot != null) {
                slot.setSize((int)slotWidth, (int)slotHeight);
            }
        }

        for (int x = 0; x < horizontalSlots; x++) {
            for (int y = 0; y < verticalSlots; y++) {
                int i = index(x, y);
                if (slots[i] == null) continue;
                slots[i].reloadLayout(
                        (int) (getRealX() + widthPadding + x * (widthPadding + slotWidth)),
                        (int) (getRealY() + heightPadding + y * (heightPadding + slotHeight)),
                        (int) slotWidth,
                        (int) slotHeight
                );
            }
        }
    }

    public void setSlot(int x, int y, SlotGui slot) {
        setSlot(index(x, y), slot);
    }

    public void setSlot(int index, SlotGui slot) {
        if (slots[index] != null) {
            removeChild(slots[index]);
        }
        if (slot != null) {
            addChild(slot);
        }
        slots[index] = slot;
    }

    public SlotGui getSlot(int x, int y) {
        return getSlot(index(x, y));
    }

    public SlotGui getSlot(int index) {
        return slots[index];
    }

    public int getCapacity() {
        return slots.length;
    }

    private int index(int x, int y) {
        return y * horizontalSlots + x;
    }
}
