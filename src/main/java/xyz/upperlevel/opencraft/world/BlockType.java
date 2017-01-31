package xyz.upperlevel.opencraft.world;


import lombok.*;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class BlockType {

    @Getter
    @NonNull
    public final String id;

    @Getter
    @NonNull
    public final BlockShape shape;

    @Getter
    @Setter
    public boolean empty = false;

    @Getter
    @Setter
    public boolean transparent = false;

    public BlockType(String id) {
        this(id, BlockShape.empty());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof BlockType && Objects.equals(((BlockType) object).id, id);
    }

    @Override
    public String toString() {
        return id;
    }
}