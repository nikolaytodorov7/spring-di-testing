package app.invalid;

import app.classes.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceInvalid {
    @Autowired
    private Message textMessage;

    @Autowired
    private Message videoMessage;
}
