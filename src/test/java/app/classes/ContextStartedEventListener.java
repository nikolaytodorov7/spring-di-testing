package app.classes;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class ContextStartedEventListener implements ApplicationListener<ContextStartedEvent> {
    public boolean refreshed = false;

    public void onApplicationEvent(ContextStartedEvent event) {
        refreshed = true;
    }
}
