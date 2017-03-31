package xyz.upperlevel.opencraft.resource.model;

import java.util.List;

public interface Model<S extends Shape> extends Shape {

    String getId();

    List<S> getShapes();
}