package app.classes;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    public boolean refreshed = false;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        refreshed = true;
    }
}
