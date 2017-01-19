package xyz.upperlevel.opencraft.world.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;

/**
 * This is a class made to test block conditions. It
 * contains some data and checks loading and saving of them
 * during the chunk loading/unloading.
 */
public class TestBlockState extends BlockState {

    @Getter
    @Setter
    @NonNull
    private String testStr = "defaultValue"; // sets default value

    public TestBlockState(BlockId id) {
        super(id);
    }

    @Override
    public void load(Map<String, Object> data) {
        String str = (String) data.get("test_string");
        if (str != null)
            testStr = str;
    }

}