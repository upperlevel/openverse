package xyz.upperlevel.opencraft.world.block.state;

import xyz.upperlevel.opencraft.world.block.id.TestBlockId;

import java.util.HashMap;
import java.util.Map;

public class TestBlockState extends BlockState {

    private String testString = "ciao";

    public TestBlockState() {
        super(TestBlockId.$);
    }

    public String getTestString() {
        return testString;
    }

    @Override
    public void load(Map<String, Object> data) {
        testString = (String) data.get("test");
    }

    @Override
    public Map<String, Object> save() {
        Map<String, Object> result = new HashMap<>();
        result.put("test", testString);
        return result;
    }
}
