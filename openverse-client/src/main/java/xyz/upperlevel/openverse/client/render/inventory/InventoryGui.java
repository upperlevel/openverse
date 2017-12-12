package xyz.upperlevel.openverse.client.render.inventory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.openverse.inventory.Inventory;
import xyz.upperlevel.ulge.gui.Gui;

@RequiredArgsConstructor
public abstract class InventoryGui<I extends Inventory> extends Gui {
    @Getter
    private final I handle;
}
