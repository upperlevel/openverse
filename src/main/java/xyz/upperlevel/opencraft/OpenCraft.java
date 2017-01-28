package xyz.upperlevel.opencraft;

import lombok.Getter;
import xyz.upperlevel.opencraft.util.ListenerManager;

public class OpenCraft {

    public static final OpenCraft $ = new OpenCraft(); // todo tmp

    @Getter
    public final ListenerManager listenerManager = new ListenerManager();

    public static OpenCraft $() {
        return $;
    }
}
