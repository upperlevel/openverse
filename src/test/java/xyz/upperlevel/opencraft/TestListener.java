package xyz.upperlevel.opencraft;

import lombok.Getter;
import org.junit.Test;
import xyz.upperlevel.opencraft.util.Event;
import xyz.upperlevel.opencraft.util.EventFunction;
import xyz.upperlevel.opencraft.util.Listener;
import xyz.upperlevel.opencraft.util.ListenerManager;

public class TestListener implements Listener {

    public static class TestEvent implements Event {

        @Getter
        private boolean test = false;
    }

    @EventFunction
    public void test(TestEvent ev) {
        ev.test = true;
    }

    @Test
    public void checkListenerSystem() {
        ListenerManager lstMan = new ListenerManager();
        Listener lst = new TestListener();

        lstMan.register(lst);
        TestEvent ev = new TestEvent();
        lstMan.call(ev);
        assert ev.test : "Listener don't call events.";

        ev.test = false;
        lstMan.unregister(lst);
        lstMan.call(ev);
        assert !ev.test : "Listener hasn't been unregistered.";
    }
}
