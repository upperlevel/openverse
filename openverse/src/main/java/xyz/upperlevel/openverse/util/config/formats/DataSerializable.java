package xyz.upperlevel.openverse.util.config.formats;

public interface DataSerializable {
    /**
     * Serializes the object in primitives, maps, enums or other serializable objects, if the object has more values it should use a {@link java.util.Map}
     * @return the serialized object
     */
    Object serialize();
}
