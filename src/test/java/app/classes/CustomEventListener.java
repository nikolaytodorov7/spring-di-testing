package app.classes;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    public String msg;
    public String eventMessage;

    public void onApplicationEvent(CustomEvent event) {
        eventMessage = event.getMessage();
        msg = "Received event - " + eventMessage;
        System.out.println(msg);
    }
}
