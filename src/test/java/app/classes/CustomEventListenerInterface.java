package app.classes;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListenerInterface implements ApplicationListener<CustomEvent> {
    public String msg;
    public String eventMessage;

    public void onApplicationEvent(CustomEvent event) {
        eventMessage = event.getMessage();
        msg = "(INTERFACE) Received custom event - " + eventMessage;
        System.out.println(msg);
    }
}
