package xyz.upperlevel.opencraft.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlockType {

    @Getter
    @NonNull
    private final String id;
}
