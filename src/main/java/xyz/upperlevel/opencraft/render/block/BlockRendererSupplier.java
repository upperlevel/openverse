package xyz.upperlevel.opencraft.render.block;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
public class BlockRendererSupplier {

    public static final BlockRendererSupplier $ = new BlockRendererSupplier();

    @Getter
    @Setter
    @NonNull
    private BlockRenderer renderer = SimpleBlockRenderer.$;

    public static BlockRendererSupplier $() {
        return $;
    }
}