package app.classes;

import org.springframework.context.event.EventListener;

public class ChainEventListener {
    public String msg;
    public String eventMessage;

    @EventListener
    public CustomEvent handleStringEvent(CustomEvent event) {
        eventMessage = event.getMessage();
        if (eventMessage == null)
            return null;

        msg = "Received chain event with message - " + eventMessage;
        System.out.println(msg);

        System.out.println(event.getMessage());

        return eventMessage.equals("ChainEvent") ?
                new CustomEvent(event, msg) :
                null;
    }
}
