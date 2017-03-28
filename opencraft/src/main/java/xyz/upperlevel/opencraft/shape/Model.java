package xyz.upperlevel.opencraft.shape;

import lombok.NonNull;
import xyz.upperlevel.opencraft.physic.collision.Box;

import java.util.List;

public interface  Model {

    Box getBox();

    void add(@NonNull ModelPart part);

    void remove(@NonNull ModelPart part);

    List<ModelPart> getParts();
}