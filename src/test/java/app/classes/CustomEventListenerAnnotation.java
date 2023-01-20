package app.classes;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomEventListenerAnnotation {
    public String msg;
    public String eventMessage;

    @EventListener
    public void handleContextStart(CustomEvent event) {
        eventMessage = event.getMessage();
        msg = "(ANNOTATION) Received custom event - " + eventMessage;
        System.out.println(msg);
    }
}
