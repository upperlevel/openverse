package xyz.upperlevel.openverse.client;

import xyz.upperlevel.ulge.game.Scene;
import xyz.upperlevel.ulge.game.Stage;

public class OpenverseDirector extends Stage {

    public OpenverseDirector() {
    }

    @Override
    public void onPreEnable(Scene prev) {
        stage(new ResourceLoading());
    }
}
