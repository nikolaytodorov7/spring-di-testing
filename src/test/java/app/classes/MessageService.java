package app.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessageService {
    @Autowired
    @Qualifier("textMessage")
    private Message textMessage;

    @Qualifier("videoMessage")
    @Autowired
    private Message videoMessage;

    public Message getTextMessage() {
        return textMessage;
    }

    public Message getVideoMessage() {
        return videoMessage;
    }
}
