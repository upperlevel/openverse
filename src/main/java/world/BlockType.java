package world;


import lombok.*;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class BlockType {

    @Getter
    @NonNull
    private final String id;

    @Getter
    @NonNull
    private final BlockShape shape;

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