package xyz.upperlevel.opencraft.client.resource.model;

import lombok.Getter;
import lombok.NonNull;
import xyz.upperlevel.opencraft.common.physic.collision.Box;

import java.util.ArrayList;
import java.util.List;

public interface Model {

    String getId();

    Box getBox();
}
