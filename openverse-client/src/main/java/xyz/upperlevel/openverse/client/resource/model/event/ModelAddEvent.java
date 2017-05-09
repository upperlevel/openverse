package xyz.upperlevel.openverse.client.resource.model.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.upperlevel.event.Event;
import xyz.upperlevel.openverse.resource.model.Model;

@RequiredArgsConstructor
public class ModelAddEvent implements Event {

    @Getter
    private final Model model;
}
