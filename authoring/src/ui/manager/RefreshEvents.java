package ui.manager;

import engine.external.events.Event;

@FunctionalInterface
public interface RefreshEvents {
    void refreshEventDispkay(Event addedEvent);
}
