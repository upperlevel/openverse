package xyz.upperlevel.openverse.util.config;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public interface Config {

    Object get(String key);

    default Object get(String key, Object defaultValue) {
        final Object res = get(key);
        return res != null ? res : defaultValue;
    }

    default Object getRequired(String key) {
        final Object res = get(key);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }

    default boolean has(String key) {
        return get(key) != null;
    }

    //------------------------String

    default String getString(String key) {
        Object raw = get(key);
        return raw == null ? null : raw.toString();
    }

    default String getString(String key, String def) {
        final String res = getString(key);
        return res != null ? res : def;
    }

    default String getStringRequired(String key) {
        String str = getString(key);
        if (str == null)
            requiredPropertyNotFound(key);
        return str;
    }

    //--------------------------StringList

    default List<String> getStringList(String key) {
        List<String> res = null;
        try {
            res = (List<String>) get(key);
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "List");
        }
        return res;
    }

    default List<String> getStringList(String key, List<String> def) {
        List<String> res = getStringList(key);
        return res != null ? res : def;
    }

    default List<String> getStringListRequired(String key) {
        List<String> res = getStringList(key);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }

    //--------------------------List

    default <T> List<T> getList(String key) {
        return (List<T>) get(key);
    }

    default <T> List<T> getList(String key, List<T> def) {
        List<T> res = getList(key);
        return res != null ? res : def;
    }

    default <T> List<T> getListRequired(String key) {
        List<T> res = getList(key);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }

    //------------------------Int

    default Integer getInt(String key) {
        Number res = null;
        try {
            res = ((Number) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Number");
        }
        return res == null ? null : res.intValue();
    }

    default int getInt(String key, int def) {
        final Integer res = getInt(key);
        return res != null ? res : def;
    }

    default int getIntRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Number) get(key)).intValue();
        } catch (ClassCastException e) {
            invalidValue(key, raw, "Number");
            return -1;
        }
    }

    //------------------------Short

    default Short getShort(String key) {
        Number res = null;
        try {
            res = ((Number) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Number");
        }
        return res == null ? null : res.shortValue();
    }

    default short getShort(String key, short def) {
        final Short res = getShort(key);
        return res != null ? res : def;
    }

    default short getShortRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Number) get(key)).shortValue();
        } catch (ClassCastException e) {
            invalidValue(key, raw, "Number");
            return -1;
        }
    }

    //------------------------Byte

    default Byte getByte(String key) {
        Number res = null;
        try {
            res = ((Number) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Number");
        }
        return res == null ? null : res.byteValue();
    }

    default byte getByte(String key, byte def) {
        final Byte res = getByte(key);
        return res != null ? res : def;
    }

    default byte getByteRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Number) get(key)).byteValue();
        } catch (ClassCastException e) {
            invalidValue(key, raw, "Number");
            return -1;
        }
    }

    //------------------------Long

    default Long getLong(String key) {
        Number res = null;
        try {
            res = ((Number) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Number");
        }
        return res == null ? null : res.longValue();
    }

    default long getLong(String key, long def) {
        final Long res = getLong(key);
        return res != null ? res : def;
    }

    default long getLongRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Number) get(key)).longValue();
        } catch (ClassCastException e) {
            invalidValue(key, raw, "Number");
            return -1;
        }
    }

    //------------------------Bool

    default Boolean getBool(String key) {
        Object raw = get(key);
        if (raw == null) return null;
        if (raw instanceof Boolean) {
            return (Boolean) raw;
        } else if (raw instanceof String) {
            switch (((String) raw).toLowerCase()) {
                case "no":
                case "false":
                    return false;
                case "yes":
                case "true":
                    return true;
            }
        } else if (raw instanceof Number) {
            return ((Number) raw).intValue() == 1;
        }
        invalidValue(key, raw, "Boolean");
        return null;
    }

    default boolean getBool(String key, boolean def) {
        final Boolean res = getBool(key);
        return res != null ? res : def;
    }

    default boolean getBoolRequired(String key) {
        Boolean raw = getBool(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        return raw;
    }

    //------------------------Float

    default Float getFloat(String key) {
        Number res;
        try {
            res = ((Number) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Number");
            return null;
        }
        return res == null ? null : res.floatValue();
    }

    default float getFloat(String key, float def) {
        Float res = getFloat(key);
        return res != null ? res : def;
    }

    default float getFloatRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Number) get(key)).floatValue();
        } catch (ClassCastException e) {
            invalidValue(key, raw, "Number");
            return -1;
        }
    }

    //------------------------Double

    default Double getDouble(String key) {
        Number res = null;
        try {
            res = ((Number) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Number");
        }
        return res == null ? null : res.doubleValue();
    }

    default double getDouble(String key, double def) {
        Double res = getDouble(key);
        return res != null ? res : def;
    }

    default double getDoubleRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Number) get(key)).doubleValue();
        } catch (ClassCastException e) {
            invalidValue(key, raw, "Number");
            return -1;
        }
    }

    //------------------------Enum

    default <T extends Enum<T>> T getEnum(String key, Class<T> clazz) {
        String raw = getString(key);
        if (raw == null) return null;
        raw = raw.replace(' ', '_').toUpperCase(Locale.ENGLISH);
        try {
            return Enum.valueOf(clazz, raw);
        } catch (IllegalArgumentException e) {
            throw new InvalidConfigurationException("Cannot find \"" + clazz.getSimpleName().toLowerCase() + "\" \"" + raw + "\"");
        }
    }

    default <T extends Enum<T>> T getEnum(String key, T def, Class<T> clazz) {
        final T res = getEnum(key, clazz);
        return res != null ? res : def;
    }

    default <T extends Enum<T>> T getEnumRequired(String key, Class<T> clazz) {
        T res = getEnum(key, clazz);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }

    //------------------------Map

    @SuppressWarnings("unchecked")//-_-
    default Map<String, Object> getSection(String key) {
        Object raw = get(key);
        if(raw == null)
            return null;
        if (raw instanceof Map)
            return (Map<String, Object>) raw;
        else
            invalidValue(key, raw, "Map");
        return null;
    }

    default Map<String, Object> getSection(String key, Map<String, Object> def) {
        final Map<String, Object> res = getSection(key);
        return res != null ? res : def;
    }

    default Map<String, Object> getSectionRequired(String key) {
        Map<String, Object> res = getSection(key);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }

    //------------------------Config

    default Config getConfig(String key, Config def) {
        Object raw = get(key);
        if (raw == null)
            return def;
        if (raw instanceof Map)
            return Config.wrap((Map<String, Object>) raw);
        else
            invalidValue(key, raw, "Map");
        return null;
    }

    default Config getConfig(String key) {
        return getConfig(key, null);
    }

    default Config getConfigRequired(String key) {
        Config res = getConfig(key, null);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }

    //------------------------Config List

    default List<Config> getConfigList(String key, List<Config> def) {
        Collection<Map<String, Object>> raw = getCollection(key);
        if (raw == null) return def;
        return raw.stream()
                .map(Config::wrap)
                .collect(Collectors.toList());
    }

    default List<Config> getConfigList(String key) {
        return getConfigList(key, null);
    }

    default List<Config> getConfigListRequired(String key) {
        List<Config> res = getConfigList(key, null);
        if (res == null)
            requiredPropertyNotFound(key);
        return res;
    }


    //------------------------Collection

    default Collection getCollection(String key) {
        try {
            return ((Collection) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Collection");
            return null;
        }
    }

    default Collection getCollection(String key, Collection def) {
        Collection found = getCollection(key);
        return found == null ? def : found;
    }

    default Collection getCollectionRequired(String key) {
        Object raw = get(key);
        if (raw == null)
            requiredPropertyNotFound(key);
        try {
            return ((Collection) get(key));
        } catch (ClassCastException e) {
            invalidValue(key, get(key), "Collection");
            return null;
        }
    }

    static void requiredPropertyNotFound(String key) {
        throw new RequiredPropertyNotFoundException(key);
    }

    static void invalidValue(String key, Object value, String expected) {
        throw new InvalidVauleConfigException(key, value, expected);
    }

    static Config wrap(Map<String, Object> map) {
        return map::get;
    }

    static Config json(File file) {
        try {
            return json(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Cannot find file: " + file, e);
        }
    }

    static Config json(Reader reader) {
        return wrap(new Gson().fromJson(reader, Map.class));
    }
}
