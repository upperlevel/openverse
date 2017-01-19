package xyz.upperlevel.opencraft.world.util;

import java.util.Map;

public interface Savable {

    void load(Map<String, Object> data);

    Map<String, Object> save();
}